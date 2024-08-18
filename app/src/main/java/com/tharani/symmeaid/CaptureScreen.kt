package com.tharani.symmeaid

import android.Manifest
import android.content.Context
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceContour
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.FaceLandmark
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CaptureScreen(navController: NavHostController) {
    val context = LocalContext.current
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

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
        CameraPreview(context = context, faceDetector = faceDetector, navController)
    } else {
        // Handle permission not granted scenario
    }
}

@Composable
fun CameraPreview(context: Context, faceDetector: FaceDetector, navController: NavController) {
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
                            processImageProxy(
                                faceDetector = faceDetector,
                                imageProxy = imageProxy,
                                overlayView = overlayView,
                                previewView = previewView,
                                navController = navController
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

@androidx.annotation.OptIn(ExperimentalGetImage::class)
private fun processImageProxy(
    faceDetector: FaceDetector,
    imageProxy: ImageProxy,
    overlayView: OverlayView,
    previewView: PreviewView,
    navController: NavController
) {
    val mediaImage = imageProxy.image
    if (mediaImage != null) {
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

        faceDetector.process(image)
            .addOnSuccessListener { faces ->
                if (faces.isNotEmpty()) {
                    Log.d("FaceDetection", "Faces detected: ${faces.size}")
                    navController.popBackStack()
                    navController.navigate("HomePage")
                    
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
