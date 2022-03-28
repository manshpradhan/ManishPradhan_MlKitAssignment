package com.example.mlkitassignment

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.FaceLandmark


class MainActivity : AppCompatActivity() {

    lateinit var btnFace : Button
    lateinit var selectedImage: Bitmap
    lateinit var graphicOverlay: GraphicOverlay

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnFace.setOnClickListener {
            runFaceContourDetection()
        }
    }
    fun runFaceContourDetection() {
        val image = InputImage.fromBitmap(selectedImage, 0)
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .build()

        btnFace.isEnabled = false

        val detector = FaceDetection.getClient(options)
        detector.process(image)
            .addOnSuccessListener {
                btnFace.isEnabled = true
                processFaceContourDetectionResult(it)
            }
            .addOnFailureListener { exception ->

                btnFace.isEnabled = true
                exception.printStackTrace()
            }
    }

    private fun processFaceContourDetectionResult(faces: List<Face>) {

        if (faces.isEmpty()) {
            showToast("No face Found")
            return
        }

        graphicOverlay.clear()
        for (face in faces) {
            val leftEar = face.getLandmark(FaceLandmark.LEFT_EAR)
            leftEar?.let {
                val leftEarPos = leftEar.position
            }

            if (face.smilingProbability != null) {
                val smileProb = face.smilingProbability
            }
            if (face.rightEyeOpenProbability != null) {
                val rightEyeOpenProb = face.rightEyeOpenProbability
            }

            if (face.trackingId != null) {
                val id = face.trackingId
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}