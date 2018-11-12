package com.programmer74.signalprocessing.visuals

import com.programmer74.signalprocessing.customnumerics.Numeric
import java.awt.Dimension
import java.awt.Graphics
import java.awt.image.BufferedImage
import javax.swing.JFrame

class Array2DForm : JFrame {

  val values: Array<Array<Numeric>>
  val image: BufferedImage

  val w: Int
  val h: Int

  constructor(caption: String, values: Array<Array<Numeric>>) : super(caption) {
    this.values = values
    w = values.size
    h = values[0].size
    image = BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR)
    fillImage()
    preferredSize = Dimension(w, h)
    size = Dimension(w, h)
  }

  fun fillImage() {
    var max = 0.0
    for (x in 0 until w) {
      for (y in 0 until h) {
        if (max < values[x][y].getValue()) {
          max = values[x][y].getValue()
        }
      }
    }

    for (x in 0 until w) {
      for (y in 0 until h) {
        val br = (values[x][y].getValue() / max * 255).toInt()
        val rgb = br.shl(16).or(br.shl(8)).or(br)
        image.setRGB(x, y, rgb)
      }
    }
  }

  override fun paint(g: Graphics) {

    var px = 0
    var py = 0
    var pw: Int = w
    var ph: Int = h

    g.drawImage(image, px, py, pw, ph, null)

  }
}