package com.rgssdeveloper.relaxamento.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.rgssdeveloper.relaxamento.R
import com.rgssdeveloper.relaxamento.databinding.FragmentMusicSelectBinding
import com.rgssdeveloper.relaxamento.firestore.Dao
import com.rgssdeveloper.relaxamento.layoutcomponents.LoadingDialog
import com.rgssdeveloper.relaxamento.viewmodel.ViewModelMusicSelect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MusicSelectFragment : Fragment(R.layout.fragment_music_select) {
    private val TAG by lazy { this::class.java.simpleName }
    private lateinit var binding: FragmentMusicSelectBinding
    private lateinit var viewModel: ViewModelMusicSelect
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_music_select, container, false)
        viewModel = ViewModelProvider(this).get(ViewModelMusicSelect::class.java)
        binding.viewmodel = viewModel
        val loadingDialog = LoadingDialog(requireActivity())
        loadingDialog.startLoadingDialog()
//        binding.selected.setOnClickListener {
//            binding.root.findViewById<com.example.justrelaxbreathe.circleimageview.CircleImageView>(it.id).apply{
//                borderWidth=4
//            }
//        }
        viewModel.navigateToPlayMusic.observe(viewLifecycleOwner, Observer { navigate->
            if(navigate){
                goToMusicPlayFragment()
                viewModel.onNavigatedToPlayMusic()
            }
        })
        //Firebase network calls
        lifecycleScope.launch(Dispatchers.Main) {
            val result = Dao().getDataFromFireStore()
            if(result.size!=6){
                noInternetNotify()
            }
            else {
                result.forEachIndexed { index, element ->
                    viewModel.getDataFromId.values.toTypedArray()[index].urls.addAll(element.urls.filterNotNull())
                    viewModel.getDataFromId.values.toTypedArray()[index].gifUrls.addAll(element.gifUrls.filterNotNull())
                }
            }
            loadingDialog.dismissDialog()
        }
        return binding.root
    }
    private fun goToMusicPlayFragment(){
        val url = viewModel.getDataFromId.getValue(viewModel.selected).getRandomUrl()
        val gifUrl = viewModel.getDataFromId.getValue(viewModel.selected).getRandomGifUrl()
        val action = MusicSelectFragmentDirections.actionFragmentMusicSelectToFragmentMusicPlay(url,gifUrl)
        findNavController(this).navigate(action)
    }
    private fun noInternetNotify(){
        Toast.makeText(context,"Error: Network Problem",Toast.LENGTH_LONG).show()
    }
}
