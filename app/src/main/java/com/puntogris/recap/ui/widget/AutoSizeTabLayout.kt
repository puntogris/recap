package com.puntogris.recap.ui.widget

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.tabs.TabLayout
import com.puntogris.recap.R

class AutoSizeTabLayout(context: Context, attrs: AttributeSet) : TabLayout(context, attrs) {

    override fun newTab(): Tab {
        return super.newTab().apply {
            setCustomView(R.layout.auto_size_tab_text)
        }
    }
}