package com.kiylx.camerax_lib.main.store

import android.location.Location
import android.os.Parcelable
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY
import androidx.camera.core.MirrorMode
import com.kiylx.camerax_lib.main.manager.video.CameraRecordQuality
import com.kiylx.camerax_lib.utils.DataSize
import com.kiylx.camerax_lib.utils.DataSize.Companion.kb
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoRecordConfig(
    /**
     * 设置用于录制的预期视频编码比特率。
     * 目标视频编码比特率尝试使实际视频编码比特率接近请求的比特率。比特率在录制过程中可能会有所不同，具体取决于录制的场景。
     * 将对请求的比特率进行额外的检查，以确保指定的比特率适用，有时传递的比特率会在内部更改，以确保视频录制可以根据平台的功能顺利进行。
     * 此 API 仅影响视频流，不应被视为整个录制的目标。音频流的比特率不受此 API 的影响。
     * 如果未调用此方法（此处不进行修改，还是0的状态下），则默认选择适当的比特率进行普通视频录制。仅当需要自定义比特率时才调用此方法。
     */
    var encodingBitRate: Int = 0,

    /**
     * 默认位置信息
     * Sets a Location object representing a geographic location where the video was recorded.
     * When use with Recorder, the geographic location is stored in udta box if the output format is MP4, and is ignored for other formats. The geographic location is stored according to ISO-6709 standard.
     * If null, no location information will be saved with the video. Default value is null.
     */
    var location: Location? = null,

    /**
     * 录制时长
     * Sets the limit for the video duration in milliseconds.
     * When used to generate recording with Recorder, if the specified duration limit is reached while the recording is being recorded, the recording will be finalized with VideoRecordEvent.Finalize.ERROR_DURATION_LIMIT_REACHED.
     * If not set or set with zero, the duration will be unlimited. If set with a negative value, an IllegalArgumentException will be thrown.
     */
    var durationLimitMillis: Long = 0,

    /**
     * 文件大小限制，为0无限制
     * Sets the limit for the file length in bytes.
     * When used with Recorder to generate recording, if the specified file size limit is reached while the recording is being recorded, the recording will be finalized with VideoRecordEvent.Finalize.ERROR_FILE_SIZE_LIMIT_REACHED.
     * If not set or set with zero, the file size will be unlimited. If set with a negative value, an IllegalArgumentException will be thrown.
     */
    var fileSizeLimit: DataSize = 0.kb,

    /**
     * 视频录制质量，查看[CameraRecordQuality]，当设置的视频拍摄质量不支持时，将会自动寻找支持的最高质量
     */
    var quality: Int = CameraRecordQuality.HD,

    /**
     * 实验特性
     * 持久性录制，若开启此特性，必须手动调用停止录制，
     * 不会因为生命周期销毁或没有界面而停止
     * 在切换摄像头时保持录制而不停止
     */
    var asPersistentRecording: Boolean = false,

    /**
     * 镜像设置
     */
    var mirrorMode: Int = MirrorMode.MIRROR_MODE_ON_FRONT_ONLY,
) : Parcelable

/**
 * capture image config
 */
@Parcelize
data class ImageCaptureConfig(
    /**
     * 水平翻转
     */
    var horizontalMirrorMode: Int = MirrorMode.MIRROR_MODE_ON_FRONT_ONLY,
    /**
     * 垂直翻转
     */
    var verticalMirrorMode: Int = MirrorMode.MIRROR_MODE_ON_FRONT_ONLY,
    /**
     * 默认位置信息
     * Sets a Location object representing a geographic location where the video was recorded.
     * When use with Recorder, the geographic location is stored in udta box if the output format is MP4, and is ignored for other formats. The geographic location is stored according to ISO-6709 standard.
     * If null, no location information will be saved with the video. Default value is null.
     */
    var location: Location? = null,
    /**
     * jpeg quality
     * @IntRange(from = 1, to = 100)
     */
    var jpegQuality: Int = 100,
    /**
     * [ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY]：缩短图片拍摄的延迟时间。
     * [ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY]：提高图片拍摄的图片质量。
     * 如果设备不符合最低要求，CameraX 便会回退到 CAPTURE_MODE_MINIMIZE_LATENCY。
     *
     * 零快门延迟仅适用于图片拍摄用例。无法为视频拍摄用例或相机扩展程序启用该功能。
     * 由于使用闪光灯会增加延迟时间，因此当闪光灯开启或处于自动模式时，零快门延迟将不起作用。
     */
    var captureMode:Int =ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY

) : Parcelable