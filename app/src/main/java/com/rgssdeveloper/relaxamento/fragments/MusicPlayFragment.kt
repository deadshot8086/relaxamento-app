package com.rgssdeveloper.relaxamento.fragments

import android.content.Intent
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.media.session.PlaybackState.*
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.rgssdeveloper.relaxamento.appLink
import com.rgssdeveloper.relaxamento.databinding.FragmentMusicPlayBinding
import com.rgssdeveloper.relaxamento.layoutcomponents.LoadingDialog
import com.rgssdeveloper.relaxamento.service.MediaPlayerService
import com.rgssdeveloper.relaxamento.viewmodel.ViewModelMusicPlay
import com.rgssdeveloper.relaxamento.viewmodelFactory.MusicPlayViewModelFactory

class MusicPlayFragment : Fragment() {
    private val TAG by lazy { this::class.java.simpleName }
    private val args by navArgs<MusicPlayFragmentArgs>()
    private val viewModel: ViewModelMusicPlay by viewModels { MusicPlayViewModelFactory(args.url) }
    private lateinit var binding: FragmentMusicPlayBinding
    private var gifDrawable:Drawable? = null
    companion object Foo{//TODO:Memory leak

        private var loadingDialog:LoadingDialog? =null
        fun stopLoading(){
            loadingDialog?.dismissDialog()
            loadingDialog=null
        }
    }
//    private lateinit var mService: MediaPlayerService
//    private var mBound: Boolean = false
//    /** Defines callbacks for service binding, passed to bindService()  */
//    private val connection = object : ServiceConnection {
//        override fun onServiceConnected(className: ComponentName, service: IBinder) {
//            // We've bound to LocalService, cast the IBinder and get LocalService instance
//            val binder = service as MediaPlayerService.LocalBinder
//            mService = binder.getService()
//            mBound = true
//        }
//        override fun onServiceDisconnected(arg0: ComponentName) {
//            mBound = false
//        }
//    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMusicPlayBinding.inflate(layoutInflater)
        binding.viewmodel = viewModel
        if(savedInstanceState==null){
            loadingDialog = LoadingDialog(requireActivity())
            loadingDialog?.startLoadingDialog()

            requireActivity().startService(Intent(activity, MediaPlayerService::class.java).also { intent ->
//            requireActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE)
            intent.action = ACTION_PREPARE.toString()
            intent.putExtra("URL","testaudio big")//TODO:Change url value to args.url
        })
        }
        Glide.with(requireActivity()).asGif().load(args.gifUrl).listener(
            object : RequestListener<GifDrawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Toast.makeText(context,"Gif loading failed, Network Problem",Toast.LENGTH_LONG).show()
                    return false
                }
                override fun onResourceReady(
                    resource: GifDrawable?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    dataSource: com.bumptech.glide.load.DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    Toast.makeText(context,"Successfully loaded Gif",Toast.LENGTH_SHORT).show()
                    return false
                }

            }).into(binding.gif)
        viewModel.statePlay.observe(viewLifecycleOwner, Observer {
            if(it){
                requireActivity().startService(Intent(activity, MediaPlayerService::class.java).also { intent ->
                    intent.action = ACTION_PLAY.toString()
                    binding.buttonPlay.setImageResource(android.R.drawable.ic_media_pause)
                    if(gifDrawable==null)
                        gifDrawable = binding.gif.drawable
                    (gifDrawable as Animatable?)?.start()
                })}
            else{
                requireActivity().startService(Intent(activity, MediaPlayerService::class.java).also { intent ->
                    intent.action = ACTION_PAUSE.toString()
                    binding.buttonPlay.setImageResource(android.R.drawable.ic_media_play)
                    if(gifDrawable==null)
                        gifDrawable = binding.gif.drawable
                    (gifDrawable as Animatable?)?.stop()
                })}
        })
        binding.buttonShare.setOnClickListener {
            val sendIntent = Intent().apply{
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Checkout this app and relax your self $appLink")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
        binding.buttonRate.setOnClickListener {
        val openIntent = Intent().apply{
            action = Intent.ACTION_VIEW
            data = Uri.parse(appLink)
        }
        startActivity(openIntent)
    }
        return binding.root
    }
    override fun onDestroy() {
        Log.i(TAG,"destroyed")
        super.onDestroy()
        if(requireActivity().isFinishing)
            requireActivity().stopService(Intent(activity, MediaPlayerService::class.java))
//          this.context?.unbindService(connection)
    }
//    override fun onDestroyView() {
//        super.onDestroyView()
//        requireActivity().stopService(Intent(activity, MediaPlayerService::class.java))
//        this.context?.unbindService(connection)
//        mBound = false
//    }
}