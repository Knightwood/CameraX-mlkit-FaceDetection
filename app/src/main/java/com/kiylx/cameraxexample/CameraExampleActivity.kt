package com.kiylx.cameraxexample

import android.content.Intent
import android.view.View
import com.kiylx.camerax_lib.main.manager.model.CaptureMode
import com.kiylx.camerax_lib.main.manager.model.FlashModel
import com.kiylx.camerax_lib.main.manager.model.ManagerConfig
import kotlinx.android.synthetic.main.activity_camera_example.*

class CameraExampleActivity : BaseCameraXActivity() {
    private var cacheMediasDir = "" //存储路径

    /**
     * 这里直接构建了配置，我没有使用intent传入配置。
     */
    override fun configAll(intent: Intent): ManagerConfig {
        cacheMediasDir = "${application.getExternalFilesDir(null)}/dcim"
        val useImageDetection = intent.getBooleanExtra(ImageDetection, false)
        return ManagerConfig().apply {
            this.cacheMediaDir = cacheMediasDir
            this.imageDetector = useImageDetection
            this.captureMode = CaptureMode.takePhoto
            this.flashMode = FlashModel.CAMERA_FLASH_AUTO
        }
    }

    override fun closeActivity(shouldInvokeFinish: Boolean) {
        cameraXFragment.stopTakeVideo(0)

        if (shouldInvokeFinish) {
            mBaseHandler.postDelayed(Runnable {
                this.finish()
            }, 500)
        }
    }

    override fun cameraFinishInited() {
        if (cameraConfig.imageDetector) {//人脸识别拍摄
            camera_control_layout.visibility = View.GONE
        } else {
            //capture()//自动拍照或录像
        }
    }

    /**
     * 启动后自动拍照或录像
     */
    private fun capture() {
        if (cameraConfig.captureMode == CaptureMode.takePhoto) {
            //拍照
            mBaseHandler.postDelayed(Runnable {
                cameraXFragment.takePhoto()
            }, 300)
        } else if (cameraConfig.captureMode == CaptureMode.takeVideo) {
            cameraXFragment.takeVideo()
        }
    }

    /**
     * 人脸识别后拍摄照片
     */
    override fun captureFace() {
        mBaseHandler.post {
            cameraXFragment.takePhoto()
        }
        /*
        //还可以使用预览画面里的bitmap存储为图片
        mBaseHandler.post {
            val bitmap = cameraXFragment.provideBitmap()
            if (bitmap != null) {
                // TODO: 存储bitmap
            }
        }*/

    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}