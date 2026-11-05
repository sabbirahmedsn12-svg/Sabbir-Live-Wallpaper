package com.sabbir.livewallpaper

import android.app.Activity
import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scroll = ScrollView(this)
        val layout = LinearLayout(this)

        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        layout.setPadding(28, 35, 28, 35)
        layout.setBackgroundColor(Color.rgb(5, 35, 27))

        scroll.addView(layout)

        val title = TextView(this)
        title.text = "🌙 Sabbir Live Wallpaper"
        title.textSize = 27f
        title.setTextColor(Color.rgb(245, 210, 135))
        title.gravity = Gravity.CENTER
        title.typeface = Typeface.DEFAULT_BOLD
        layout.addView(title)

        val subtitle = TextView(this)
        subtitle.text = "বাংলা • العربية • English"
        subtitle.textSize = 18f
        subtitle.setTextColor(Color.WHITE)
        subtitle.gravity = Gravity.CENTER
        subtitle.setPadding(0, 25, 0, 30)
        layout.addView(subtitle)

        val preview = TextView(this)
        preview.text = """
            
            بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ

                     09:45 AM

                  বৃহস্পতিবার

                  ২০ কার্তিক ১৪৩৩

                  ৫ নভেম্বর ২০২৬

                  ১৪ জমাদিউল আউয়াল ১৪৪৮

                    سُبْحَانَ اللَّهِ
        """.trimIndent()

        preview.textSize = 21f
        preview.gravity = Gravity.CENTER
        preview.setTextColor(Color.WHITE)
        preview.setPadding(15, 35, 15, 35)
        preview.setBackgroundColor(Color.rgb(14, 75, 55))
        layout.addView(preview)

        val button = Button(this)
        button.text = "Set Live Wallpaper"
        button.textSize = 18f

        button.setOnClickListener {
            try {
                val intent =
                    Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)

                intent.putExtra(
                    WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                    ComponentName(this, WallpaperService::class.java)
                )

                startActivity(intent)

            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    "Wallpaper চালু করা যায়নি",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        layout.addView(
            button,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                65
            )
        )

        val footer = TextView(this)
        footer.text = "Designed by Sabbir Ahmed"
        footer.textSize = 15f
        footer.setTextColor(Color.LTGRAY)
        footer.gravity = Gravity.CENTER
        footer.setPadding(0, 35, 0, 0)
        layout.addView(footer)

        setContentView(scroll)
    }
}
