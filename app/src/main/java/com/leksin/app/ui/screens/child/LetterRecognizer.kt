package com.leksin.app.ui.screens.child

import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.math.min

/**
 * Interface abstraction u/ Letter Recognition (OCR / On-Device AI).
 */
interface LetterRecognizer {
    suspend fun recognize(bitmap: Bitmap): Char?
}

class MlKitLetterRecognizer : LetterRecognizer {
    override suspend fun recognize(bitmap: Bitmap): Char? = suspendCoroutine { cont ->
        try {
            // Crop region untuk frame huruf.
            val croppedBitmap = cropCenterSquare(bitmap)
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            val image = InputImage.fromBitmap(croppedBitmap, 0)

            recognizer.process(image)
                .addOnSuccessListener { result ->
                    // 1. Coba raw text
                    var detectedChar = result.text.uppercase().firstOrNull { it.isLetter() }

                    // 2. Fallback u/ mencari blok teks
                    if (detectedChar == null) {
                        for (block in result.textBlocks) {
                            for (line in block.lines) {
                                val found = line.text.uppercase().firstOrNull { it.isLetter() }
                                if (found != null) {
                                    detectedChar = found
                                    break
                                }
                            }
                            if (detectedChar != null) break
                        }
                    }

                    cont.resume(detectedChar)
                }
                .addOnFailureListener {
                    cont.resume(null)
                }
        } catch (e: Exception) {
            cont.resume(null)
        }
    }

    private fun cropCenterSquare(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val size = (min(width, height) * 0.7f).toInt()
        val x = (width - size) / 2
        val y = (height - size) / 2
        return try {
            Bitmap.createBitmap(bitmap, x, y, size, size)
        } catch (e: Exception) {
            bitmap
        }
    }
}
