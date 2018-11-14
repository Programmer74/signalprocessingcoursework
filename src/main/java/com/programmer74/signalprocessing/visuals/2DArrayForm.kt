package com.programmer74.signalprocessing.visuals

import com.programmer74.signalprocessing.customnumerics.Numeric
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.image.BufferedImage
import javax.swing.JFrame

class Array2DForm : JFrame {

  val values: Array<Array<Numeric>>
  val image: BufferedImage

  val w: Int
  val h: Int

  var selectedi: Int = -1
  var selectedj: Int = -1

  val originalCaption: String

  constructor(caption: String, values: Array<Array<Numeric>>, wantedW: Int, wantedH: Int) : super(caption) {
    this.values = values
    w = values.size
    h = values[0].size
    image = BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR)
    originalCaption = caption
    fillImage()
    preferredSize = Dimension(wantedW, wantedH)
    size = Dimension(wantedW, wantedH)
    addMouseListener(object: MouseAdapter() {
      override fun mouseClicked(e: MouseEvent?) {
        super.mouseClicked(e)
        mouseClickedHandler(e!!)
      }
    })
    addKeyListener(object: KeyAdapter() {
      override fun keyPressed(e: KeyEvent?) {
        super.keyReleased(e)
        keyPressedHandler(e!!)
      }
    })
  }

  fun mouseClickedHandler(e: MouseEvent) {
    val x: Double = e.x.toDouble() / width.toDouble()
    val y: Double = e.y.toDouble() / height.toDouble()
    val i = (x * w).toInt()
    val j = (y * h).toInt()
    selectedi = i
    selectedj = j
    repaint()
  }

  fun keyPressedHandler(e: KeyEvent) {
    when (e.keyCode) {
      KeyEvent.VK_LEFT -> selectedi--
      KeyEvent.VK_RIGHT -> selectedi++
      KeyEvent.VK_UP -> selectedj--
      KeyEvent.VK_DOWN -> selectedj++
    }
    if (selectedi < 0) selectedi = 0
    if (selectedi >= w) selectedi = w - 1
    if (selectedj < 0) selectedj = 0
    if (selectedj >= h) selectedj = h - 1
    repaint()
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
    var pw: Int = width
    var ph: Int = height

    g.drawImage(image, px, py, pw, ph, null)

    if (selectedi >= 0) {
      val value = values[selectedi][selectedj]
      px = (selectedi.toDouble() / w.toDouble() * width).toInt()
      py = (selectedj.toDouble() / h.toDouble() * height).toInt()
      pw = (1.0 / w.toDouble() * width).toInt()
      ph = (1.0 / h.toDouble() * height).toInt()
      g.color = Color.RED
      g.drawRect(px, py, pw, ph)
      title = "$originalCaption [$selectedi;$selectedj] = ${value.getValue()}"
    }

  }
}