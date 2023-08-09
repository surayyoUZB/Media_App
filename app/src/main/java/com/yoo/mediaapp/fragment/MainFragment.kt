package com.yoo.mediaapp.fragment


import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.yoo.mediaapp.adapter.RvAdapter
import com.yoo.mediaapp.MainActivity
import com.yoo.mediaapp.database.Media
import com.yoo.mediaapp.R
import com.yoo.mediaapp.util.Constant
import com.yoo.mediaapp.databinding.FragmentMainBinding
import com.yoo.mediaapp.viewModel.MainViewModel
import com.yoo.mediaapp.viewModel.OnEvent
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class MainFragment() : Fragment() {

    private val viewModel by viewModels<MainViewModel>()

    private var _binding:FragmentMainBinding?=null
    private val binding get() = _binding!!
    private val mainActivity by lazy { MainActivity() }
    lateinit var toggle: ActionBarDrawerToggle
    var sth: Boolean=false

    private var mediaPlayer: MediaPlayer?=null
    private lateinit var handler: Handler
    private lateinit var seekBar: SeekBar
    private lateinit var name: TextView
    private lateinit var startTime: TextView
    private lateinit var endTime: TextView
    private lateinit var btnGo: ImageView
    private lateinit var btnBack: ImageView
    private lateinit var btnStart: ImageView

    private lateinit var rvAdapter: RvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val view=LayoutInflater.from(requireContext()).inflate(R.layout.bottom_sheet, null)
        btnGo=view.findViewById(R.id.btnGo)
        btnBack=view.findViewById(R.id.btnBack)

        rvAdapter= RvAdapter()
        binding.rv.apply {
            layoutManager= LinearLayoutManager(requireContext())
            adapter=rvAdapter
            rvAdapter.mediaList=Constant.list
//            rvAdapter.mediaList.addAll(viewModel.mediaList)
//            if (viewModel.mediaList.isNotEmpty()){
//                rvAdapter.mediaList=viewModel.mediaList
//            }else{
//                Log.d("nulllllllllllllll", "onViewCreated: nulllllllllllllllllllllllll")
//            }
        }
//        binding.btn.setOnClickListener {
//            viewModel.onEvent(OnEvent.OnSaveMedia(Media("salom", "time", R.raw.audio9_b, false, 0)))
//            Log.d("^^^^^^", "onViewCreated: ${viewModel.mediaList.size}")
//            Log.d("^^^^^^", "onViewCreated: ${viewModel.mediaList.isNotEmpty()}")
//        }


        rvAdapter.onClick={ media, position->
            showBottomSheet(
                position
            )
            viewModel.onEvent(OnEvent.OnUpdateMedia(media))
            Log.d("@@@update", "onViewCreated: $media")
        }
}





    private fun showBottomSheet(
        position: Int
    ) {
        val bottomSheet = BottomSheetDialog(requireContext())
        val view=LayoutInflater.from(requireContext()).inflate(R.layout.bottom_sheet, null)
        seekBar=view.findViewById(R.id.seekBar)
        name=view.findViewById(R.id.name)
        startTime=view.findViewById(R.id.startTime)
        endTime=view.findViewById(R.id.endTime)
        btnGo=view.findViewById(R.id.btnGo)
        btnBack=view.findViewById(R.id.btnBack)
        btnStart=view.findViewById(R.id.btnStant)

        handler = Handler(Looper.getMainLooper())
        bottomSheet.setContentView(view)

        val runnable=object:Runnable{
            override fun run() {
                try {
                    if (seekBar.progress < mediaPlayer?.duration!!) {
                        handler.postDelayed(this, 100)
                        startTime.text = setCurrentDuration(mediaPlayer?.currentPosition!!.toLong())
                        seekBar.progress = mediaPlayer?.currentPosition!!
                    } else {
                        mediaPlayer = null
                        bottomSheet.dismiss()
                    }
                }catch (e:Exception){

                }
            }
        }

        btnGo.setOnClickListener {
            mediaPlayer = MediaPlayer.create(requireContext(), rvAdapter.mediaList[position + 1].audio)
            handler.postDelayed(runnable, 100)
            seekBar.max = mediaPlayer?.duration!!
            endTime.text = setCurrentDuration(mediaPlayer?.duration!!.toLong())
            mediaPlayer?.start()
        }


        name.text= rvAdapter.mediaList[position].name

        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(requireContext(), rvAdapter.mediaList[position].audio)
            handler.postDelayed(runnable, 100)
            seekBar.max = mediaPlayer?.duration!!
            endTime.text = setCurrentDuration(mediaPlayer?.duration!!.toLong())
            mediaPlayer?.start()


            btnStart.setOnClickListener {
                if (mediaPlayer!!.isPlaying) {
                    mediaPlayer?.pause()
                    btnStart.setImageResource(R.drawable.baseline_play_arrow_24)
                } else {
                    mediaPlayer?.start()
                    btnStart.setImageResource(R.drawable.baseline_pause_24)
                }
            }

//        binding.resume.setOnClickListener{
//            if (!mediaPlayer?.isPlaying!!){
//                mediaPlayer?.start()
//            }
//        }
//
//        binding.stop.setOnClickListener {
//            mediaPlayer?.stop()
//            mediaPlayer=null
//            binding.startTime.text="00:00"
//            binding.seekBar.progress=0
//        }
            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        startTime.text = setCurrentDuration(progress.toLong())
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    seekBar?.let {
                        mediaPlayer?.seekTo(it.progress)
                    }
                }
            })
        }
        bottomSheet.show()
        bottomSheet.setOnDismissListener {
            if (mediaPlayer != null) {
                mediaPlayer?.release()
                mediaPlayer = null
            }
               bottomSheet.dismiss()

        }

    }


    private fun setCurrentDuration(ms:Long):String{
        val dateFormat= SimpleDateFormat("mm:ss", Locale.getDefault())
        return dateFormat.format(ms)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
        if (mediaPlayer!=null){
            mediaPlayer?.release()
            mediaPlayer=null
        }
        viewModel.mediaList.clear()
    }
}
