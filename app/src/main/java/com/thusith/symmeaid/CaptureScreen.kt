package com.thusith.symmeaid

import android.Manifest
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.Size
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import kotlinx.coroutines.delay
import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.YuvImage

import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke

import android.media.Image
import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import com.thusith.symmeaid.viewModel.FaceCaptureViewModel
import java.io.ByteArrayOutputStream

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CaptureScreen(navController: NavHostController, faceCaptureViewModel: FaceCaptureViewModel) {
    val context = LocalContext.current
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    val progress = remember { mutableFloatStateOf(0f) } // Progress value

    // State variables for face position and radius
    val faceCenterX = remember { mutableStateOf<Float?>(null) }
    val faceCenterY = remember { mutableStateOf<Float?>(null) }
    val faceRadius = remember { mutableStateOf<Float?>(null) }

    LaunchedEffect(Unit) {
        cameraPermissionState.launchPermissionRequest()
    }

    val faceDetector = FaceDetection.getClient(
        FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .enableTracking()
            .build()
    )

    val overlayView = remember { OverlayView(context) }

    if (cameraPermissionState.status.isGranted) {
        Box(modifier = Modifier.fillMaxSize()) {
            CameraPreview(
                context = context,
                faceDetector = faceDetector,
                navController = navController,
                faceCaptureViewModel = faceCaptureViewModel,
                onFaceDetected = { centerX, centerY, radius ->
                    // Update the state with the detected face values
                    faceCenterX.value = centerX
                    faceCenterY.value = centerY
                    faceRadius.value = radius
                }
            )

            // Display the scanning overlay with detected face parameters
            faceCenterX.value?.let { centerX ->
                faceCenterY.value?.let { centerY ->
                    faceRadius.value?.let { radius ->
                        FaceScanningOverlay(
                            faceCenterX = centerX,
                            faceCenterY = centerY,
                            faceRadius = radius
                        )
                    }
                }
            }

            // Overlay the progress line and text
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 100.dp), // Adjust padding as necessary
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(25.dp))
                
                Text(
                    text = "Analyzing your parameters",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .height(4.dp)
                        .background(Color.Gray) // Background color of progress bar
                ) {
                    LinearProgressIndicator(
                        progress = {
                            progress.floatValue // Correct usage for determinate progress
                        },
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                    )
                }
            }
        }

        // Simulate progress increase while the camera is on
        LaunchedEffect(Unit) {
            while (progress.floatValue < 1f) {
                delay(100) // Adjust the speed of progress filling
                progress.floatValue += 0.01f
            }
        }
    } else {
        // Handle permission not granted scenario
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Camera permission is required.")
        }
    }

}

@Composable
fun CameraPreview(
    context: Context,
    faceDetector: FaceDetector,
    navController: NavController,
    faceCaptureViewModel: FaceCaptureViewModel,
    onFaceDetected: (Float, Float, Float) -> Unit
) {
    val lifecycleOwner = context as LifecycleOwner
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember { PreviewView(context) }
    val overlayView = remember { OverlayView(context) }

    AndroidView(
        factory = {
            val viewGroup = FrameLayout(context).apply {
                addView(previewView)
                addView(overlayView)
            }

            previewView.parent?.let { parent ->
                (parent as ViewGroup).removeView(previewView)
            }

            // Remove overlayView from its parent if already attached
            overlayView.parent?.let { parent ->
                (parent as ViewGroup).removeView(overlayView)
            }

            // Add the previewView and overlayView to a FrameLayout
            val frameLayout = FrameLayout(context).apply {
                addView(previewView)
                addView(overlayView)
            }

            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder()
                    .setTargetResolution(Size(1280, 720))
                    .build()

                val imageAnalyzer = ImageAnalysis.Builder()
                    .setTargetResolution(Size(1280, 720))
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also { analysisUseCase ->
                        analysisUseCase.setAnalyzer(
                            ContextCompat.getMainExecutor(context)
                        ) { imageProxy ->
                            // Determine if the front camera is being used
                            val isFrontCamera = CameraSelector.DEFAULT_FRONT_CAMERA == CameraSelector.DEFAULT_FRONT_CAMERA

                            // Call processImageProxy with the isFrontCamera flag
                            processImageProxy(
                                faceDetector = faceDetector,
                                imageProxy = imageProxy,
                                overlayView = overlayView,
                                previewView = previewView,
                                navController = navController,
                                context = context,
                                cameraProvider = cameraProvider,
                                faceCaptureViewModel = faceCaptureViewModel,
                                onFaceDetected = onFaceDetected // Pass callback
                                //isFrontCamera = isFrontCamera // Pass the mirroring flag
                            )
                        }
                    }

                val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

                preview.setSurfaceProvider(previewView.surfaceProvider)

                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageAnalyzer
                )
            }, ContextCompat.getMainExecutor(context))

            frameLayout
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun FaceScanningOverlay(
    faceCenterX: Float?,
    faceCenterY: Float?,
    faceRadius: Float?
) {
    // Only draw if face coordinates are available
    if (faceCenterX != null && faceCenterY != null && faceRadius != null) {
        // Animated value for the line's vertical movement
        val lineOffsetY by rememberInfiniteTransition(label = "").animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ), label = ""
        )

        Canvas(modifier = Modifier.fillMaxSize()) {
            // Draw face detection circle (white)
            drawCircle(
                color = Color.White,
                center = Offset(faceCenterX, faceCenterY),
                radius = faceRadius,
                style = Stroke(width = 4.dp.toPx())
            )

            // Draw scanning line moving up and down within the circle
            val lineY = faceCenterY + (lineOffsetY * faceRadius * 2) - faceRadius
            drawLine(
                color = Color.Red,
                start = Offset(faceCenterX - faceRadius, lineY),
                end = Offset(faceCenterX + faceRadius, lineY),
                strokeWidth = 4.dp.toPx(),
                cap = StrokeCap.Round // Rounded edges for the scanning line
            )
        }
    }
}


private var faceDetectionTriggered = false
private var canProcessFrame = true
private var isNavigating = false
private const val frameProcessingInterval = 3000L // 3 seconds interval
private val handler = Handler(Looper.getMainLooper())

@androidx.annotation.OptIn(ExperimentalGetImage::class)
private fun processImageProxy(
    faceDetector: FaceDetector,
    imageProxy: ImageProxy,
    overlayView: OverlayView,
    previewView: PreviewView,
    navController: NavController,
    context: Context,
    cameraProvider: ProcessCameraProvider,
    faceCaptureViewModel: FaceCaptureViewModel,
    onFaceDetected: (Float, Float, Float) -> Unit // Callback for face data
) {
    if (!canProcessFrame || faceDetectionTriggered || isNavigating) {
        imageProxy.close()
        return
    }

    val mediaImage = imageProxy.image
    if (mediaImage != null) {
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

        faceDetector.process(image)
            .addOnSuccessListener { faces ->
                if (faces.isNotEmpty()) {
                    val face = faces[0]
                    val bounds = face.boundingBox

                    val previewWidth = previewView.width.toFloat()
                    val previewHeight = previewView.height.toFloat()
                    val imageWidth = image.width.toFloat()
                    val imageHeight = image.height.toFloat()

                    val scaleFactorX = previewWidth / imageWidth
                    val scaleFactorY = previewHeight / imageHeight

                    val mirrored = true
                    val adjustment = 80f
                    val centerX = if (mirrored) {
                        previewWidth - (bounds.centerX() * scaleFactorX) - adjustment
                    } else {
                        (bounds.centerX() * scaleFactorX) - adjustment
                    }
                    val centerY = bounds.centerY() * scaleFactorY - 280
                    val radius = Math.max(bounds.width() * scaleFactorX, bounds.height() * scaleFactorY) / 2.4f

                    // Trigger callback to update face position in the composable
                    onFaceDetected(centerX, centerY, radius)

                    // Capture the image and store in Firebase
                    captureAndUploadImage(imageProxy, faceCaptureViewModel, context)

                    // **Set faceDetectionTriggered to true to prevent repeated alerts**
                    faceDetectionTriggered = true

                    // Show an alert dialog after face detection
                    Handler(Looper.getMainLooper()).postDelayed({
                        cameraProvider.unbindAll()
                        AlertDialog.Builder(context)
                            .setTitle("Face Detection")
                            .setMessage("Your face successfully detected \n wait until upload")
                            .setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()

                                // Navigate to the DisplayFace screen
                                isNavigating = true
                                navController.popBackStack()
                                navController.navigate("NotificationScreen")

                                // Reset the state for the next detection
                                faceDetectionTriggered = false
                                isNavigating = false
                            }
                            .show()
                    }, 3000)

                    // Set a delay for frame processing after face detection
                    handler.postDelayed({
                        canProcessFrame = true
                    }, frameProcessingInterval)

                } else {
                    Log.d("FaceDetection", "No faces detected")
                }
            }
            .addOnFailureListener { e ->
                Log.e("FaceDetection", "Error detecting face", e)
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    }
}

// Method to capture and save image in Firebase
@androidx.annotation.OptIn(ExperimentalGetImage::class)
private fun captureAndUploadImage(
    imageProxy: ImageProxy,
    faceCaptureViewModel: FaceCaptureViewModel,
    context: Context
) {
    val mediaImage = imageProxy.image ?: return

    // Convert ImageProxy to Bitmap
    val bitmap = imageProxyToBitmap(mediaImage, imageProxy.imageInfo.rotationDegrees)

    // Upload to Firebase using FaceCaptureViewModel
    faceCaptureViewModel.uploadCapturedFace(bitmap) { success, errorMessage ->
        if (success) {
            Toast.makeText(context, "Image uploaded successfully!", Toast.LENGTH_SHORT).show()
            Log.d("FaceCapture", "Image uploaded successfully!")
        } else {
            Toast.makeText(context, "Image upload failed: $errorMessage", Toast.LENGTH_SHORT).show()
            Log.e("FaceCapture", "Image upload failed: $errorMessage")
        }
    }
}

// Helper function to convert ImageProxy to Bitmap
private fun imageProxyToBitmap(mediaImage: Image, rotationDegrees: Int): Bitmap {
    val yBuffer = mediaImage.planes[0].buffer
    val uBuffer = mediaImage.planes[1].buffer
    val vBuffer = mediaImage.planes[2].buffer

    val ySize = yBuffer.remaining()
    val uSize = uBuffer.remaining()
    val vSize = vBuffer.remaining()

    val nv21 = ByteArray(ySize + uSize + vSize)

    yBuffer.get(nv21, 0, ySize)
    vBuffer.get(nv21, ySize, vSize)
    uBuffer.get(nv21, ySize + vSize, uSize)

    val yuvImage = YuvImage(nv21, ImageFormat.NV21, mediaImage.width, mediaImage.height, null)
    val out = ByteArrayOutputStream()
    yuvImage.compressToJpeg(Rect(0, 0, mediaImage.width, mediaImage.height), 100, out)
    val imageBytes = out.toByteArray()

    val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    val matrix = Matrix()
    matrix.postRotate(rotationDegrees.toFloat())

    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}

