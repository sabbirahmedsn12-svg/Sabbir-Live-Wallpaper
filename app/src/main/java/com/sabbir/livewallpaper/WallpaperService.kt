package com.sabbir.livewallpaper

import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Handler
import android.os.Looper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.time.LocalDate
import java.time.chrono.HijrahDate
import java.time.temporal.ChronoField

class WallpaperService : WallpaperService() {

    override fun onCreateEngine(): Engine {
        return WallpaperEngine()
    }

    inner class WallpaperEngine : Engine() {

        private val handler = Handler(Looper.getMainLooper())
        private var visible = false

        private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        private val drawRunnable = object : Runnable {
            override fun run() {
                drawWallpaper()
                if (visible) {
                    handler.postDelayed(this, 1000)
                }
            }
        }

        override fun onVisibilityChanged(isVisible: Boolean) {
            visible = isVisible
            if (visible) {
                handler.removeCallbacks(drawRunnable)
                handler.post(drawRunnable)
            } else {
                handler.removeCallbacks(drawRunnable)
            }
        }

        override fun onSurfaceChanged(
            holder: SurfaceHolder,
            format: Int,
            width: Int,
            height: Int
        ) {
            super.onSurfaceChanged(holder, format, width, height)
            drawWallpaper()
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder) {
            visible = false
            handler.removeCallbacks(drawRunnable)
            super.onSurfaceDestroyed(holder)
        }

        private fun drawWallpaper() {

            val holder = surfaceHolder
            var canvas: Canvas? = null

            try {
                canvas = holder.lockCanvas()
                if (canvas != null) {

                    val w = canvas.width.toFloat()
                    val h = canvas.height.toFloat()

                    canvas.drawColor(Color.rgb(5, 30, 25))

                    // Background decoration
                    paint.color = Color.rgb(12, 75, 53)
                    canvas.drawCircle(w * 0.8f, h * 0.15f,
                        w * 0.35f, paint)

                    // Moon
                    paint.color = Color.rgb(255, 220, 140)
                    canvas.drawCircle(w * 0.78f, h * 0.19f,
                        w * 0.085f, paint)

                    paint.color = Color.rgb(5, 30, 25)
                    canvas.drawCircle(w * 0.82f, h * 0.16f,
                        w * 0.08f, paint)

                    // Clock
                    val now = Date()

                    val time = SimpleDateFormat(
                        "hh:mm:ss a", Locale.ENGLISH
                    ).format(now)

                    drawText(
                        canvas,
                        time,
                        w / 2,
                        h * 0.34f,
                        w * 0.105f,
                        Color.WHITE,
                        true
                    )

                    // English date
                    val englishDate = SimpleDateFormat(
                        "EEEE, dd MMMM yyyy",
                        Locale.ENGLISH
                    ).format(now)

                    drawText(
                        canvas,
                        englishDate,
                        w / 2,
                        h * 0.41f,
                        w * 0.033f,
                        Color.rgb(245, 210, 135)
                    )

                    // Bengali date
                    val today = LocalDate.now()

                    val banglaMonths = arrayOf(
                        "বৈশাখ", "জ্যৈষ্ঠ", "আষাঢ়",
                        "শ্রাবণ", "ভাদ্র", "আশ্বিন",
                        "কার্তিক", "অগ্রহায়ণ", "পৌষ",
                        "মাঘ", "ফাল্গুন", "চৈত্র"
                    )

                    val banglaYear = today.year - 593
                    val start = LocalDate.of(today.year, 4, 14)
                    val adjustedStart =
                        if (today.isBefore(start)) start.minusYears(1)
                        else start

                    val dayOfYear =
                        java.time.temporal.ChronoUnit.DAYS.between(
                            adjustedStart, today
                        ).toInt()

                    val monthLengths = intArrayOf(
                        31, 31, 31, 31, 31, 30,
                        30, 30, 30, 30, 30, 30
                    )

                    var remaining = dayOfYear
                    var monthIndex = 0

                    while (monthIndex < 11 &&
                        remaining >= monthLengths[monthIndex]) {
                        remaining -= monthLengths[monthIndex]
                        monthIndex++
                    }

                    val banglaDay = remaining + 1

                    val banglaDate =
                        "$banglaDay ${banglaMonths[monthIndex]} $banglaYear বঙ্গাব্দ"

                    drawText(
                        canvas,
                        banglaDate,
                        w / 2,
                        h * 0.47f,
                        w * 0.040f,
                        Color.WHITE
                    )

                    // Hijri date
                    try {
                        val hijri = HijrahDate.from(today)

                        val hijriMonths = arrayOf(
                            "মুহাররম", "সফর",
                            "রবিউল আউয়াল", "রবিউস সানি",
                            "জমাদিউল আউয়াল", "জমাদিউস সানি",
                            "রজব", "শাবান", "রমজান",
                            "শাওয়াল", "জিলকদ", "জিলহজ"
                        )

                        val hijriDay = hijri.get(
                            ChronoField.DAY_OF_MONTH
                        )

                        val hijriMonth = hijri.get(
                            ChronoField.MONTH_OF_YEAR
                        )

                        val hijriYear = hijri.get(
                            ChronoField.YEAR
                        )

                        val hijriDate =
                            "$hijriDay ${hijriMonths[hijriMonth - 1]} $hijriYear হিজরি"

                        drawText(
                            canvas,
                            hijriDate,
                            w / 2,
                            h * 0.53f,
                            w * 0.038f,
                            Color.rgb(245, 210, 135)
                        )

                    } catch (_: Exception) {
                    }

                    // Decorative mosque silhouette
                    paint.color = Color.rgb(3, 19, 16)
                    canvas.drawRect(
                        0f, h * 0.83f, w, h, paint
                    )

                    canvas.drawRect(
                        w * 0.25f, h * 0.77f,
                        w * 0.75f, h * 0.90f, paint
                    )

                    canvas.drawCircle(
                        w * 0.5f, h * 0.77f,
                        w * 0.13f, paint
                    )

                    canvas.drawRect(
                        w * 0.12f, h * 0.65f,
                        w * 0.17f, h * 0.90f, paint
                    )

                    canvas.drawCircle(
                        w * 0.145f, h * 0.65f,
                        w * 0.035f, paint
                    )

                    drawText(
                        canvas,
                        "سُبْحَانَ اللَّهِ",
                        w / 2,
                        h * 0.95f,
                        w * 0.055f,
                        Color.rgb(245, 210, 135)
                    )
                }

            } catch (_: Exception) {
            } finally {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas)
                }
            }
        }

        private fun drawText(
            canvas: Canvas,
            text: String,
            x: Float,
            y: Float,
            size: Float,
            color: Int,
            bold: Boolean = false
        ) {
            paint.color = color
            paint.textSize = size
            paint.textAlign = Paint.Align.CENTER
            paint.typeface = if (bold)
                Typeface.create("sans-serif", Typeface.BOLD)
            else
                Typeface.create("sans-serif", Typeface.NORMAL)

            canvas.drawText(text, x, y, paint)
        }
    }
}
