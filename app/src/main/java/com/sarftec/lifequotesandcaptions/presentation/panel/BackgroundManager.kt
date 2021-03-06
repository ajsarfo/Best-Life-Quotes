package com.sarftec.lifequotesandcaptions.presentation.panel

import com.sarftec.lifequotesandcaptions.databinding.LayoutTextPanelBinding

class BackgroundManager(
    private val panelListener: PanelListener,
    layout: LayoutTextPanelBinding
) {

    enum class Chooser {
        IMAGE, GALLERY, COLOR;
    }

    init {
        layout.readerBackgroundColor.setOnClickListener {
            panelListener.chooseBackground(Chooser.COLOR)
        }
        layout.readerBackgroundGallery.setOnClickListener {
            panelListener.chooseBackground(Chooser.GALLERY)
        }
        layout.readerBackgroundImages.setOnClickListener {
            panelListener.chooseBackground(Chooser.IMAGE)
        }
    }
}