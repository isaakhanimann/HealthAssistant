package com.example.healthassistant.ui.home.experience.timeline.ingestion

import androidx.compose.ui.graphics.Path
import com.example.healthassistant.data.substances.DurationRange
import com.example.healthassistant.data.substances.RoaDuration
import kotlin.time.Duration

interface TimelineDrawable {
    fun getStrokePath(pixelsPerSec: Float, height: Float): Path
    fun getFillPath(pixelsPerSec: Float, height: Float): Path
}

data class FullTimeline(
    val onset: FullDurationRange,
    val comeup: FullDurationRange,
    val peak: FullDurationRange,
    val offset: FullDurationRange,
) : TimelineDrawable {
    val totalMax
        get() = onset.max + comeup.max + peak.max + offset.max

    override fun getStrokePath(pixelsPerSec: Float, height: Float): Path {
        return Path().apply {
            val weight = 0.5
            val onsetEndX =
                onset.interpolateAt(weight).inWholeSeconds * pixelsPerSec
            val comeupEndX =
                onsetEndX + (comeup.interpolateAt(weight).inWholeSeconds * pixelsPerSec)
            val peakEndX =
                comeupEndX + (peak.interpolateAt(weight).inWholeSeconds * pixelsPerSec)
            val offsetEndX =
                peakEndX + (offset.interpolateAt(weight).inWholeSeconds * pixelsPerSec)
            moveTo(0f, height)
            lineTo(x = onsetEndX, y = height)
            lineTo(x = comeupEndX, y = 0f)
            lineTo(x = peakEndX, y = 0f)
            lineTo(x = offsetEndX, y = height)
        }
    }

    override fun getFillPath(pixelsPerSec: Float, height: Float): Path {
        return Path().apply {
            // path over top
            val onsetStartMinX = onset.min.inWholeSeconds * pixelsPerSec
            val comeupEndMinX = onsetStartMinX + (comeup.min.inWholeSeconds * pixelsPerSec)
            val peakEndMaxX =
                (onset.max + comeup.max + peak.max).inWholeSeconds * pixelsPerSec
            val offsetEndMaxX =
                peakEndMaxX + (offset.max.inWholeSeconds * pixelsPerSec)
            moveTo(onsetStartMinX, height)
            lineTo(x = comeupEndMinX, y = 0f)
            lineTo(x = peakEndMaxX, y = 0f)
            lineTo(x = offsetEndMaxX, y = height)
            // path bottom back
            val offsetEndMinX =
                (onset.min + comeup.min + peak.min + offset.min).inWholeSeconds * pixelsPerSec
            val peakEndMinX =
                (onset.min + comeup.min + peak.min).inWholeSeconds * pixelsPerSec
            val comeupEndMaxX =
                (onset.max + comeup.max).inWholeSeconds * pixelsPerSec
            val onsetStartMaxX = onset.max.inWholeSeconds * pixelsPerSec
            lineTo(x = offsetEndMinX, y = height)
            lineTo(x = peakEndMinX, y = 0f)
            lineTo(x = comeupEndMaxX, y = 0f)
            lineTo(x = onsetStartMaxX, y = height)
            close()
        }
    }
}

data class FullDurationRange(
    val min: Duration,
    val max: Duration
) {
    fun interpolateAt(value: Double): Duration {
        val diff = max - min
        return min + diff.times(value)
    }
}

data class TotalTimeline(
    val total: FullDurationRange,
    val weight: Double = 0.5,
    val percentSmoothness: Float = 0.5f,
) : TimelineDrawable {
    override fun getStrokePath(pixelsPerSec: Float, height: Float): Path {
        return Path().apply {
            val totalMinX = (total.min.inWholeSeconds) * pixelsPerSec
            val totalX = total.interpolateAt(weight).inWholeSeconds * pixelsPerSec
            moveTo(0f, height)
            endSmoothLineTo(
                percentSmoothness = percentSmoothness,
                startX = 0f,
                endX = totalMinX / 2,
                endY = 0f
            )
            startSmoothLineTo(
                percentSmoothness = percentSmoothness,
                startX = totalMinX / 2,
                startY = 0f,
                endX = totalX,
                endY = height
            )
        }
    }

    override fun getFillPath(pixelsPerSec: Float, height: Float): Path {
        return Path().apply {
            // path over top
            val totalMinX = (total.min.inWholeSeconds) * pixelsPerSec
            val totalMaxX = (total.max.inWholeSeconds) * pixelsPerSec
            moveTo(x = totalMinX / 2, y = 0f)
            startSmoothLineTo(
                percentSmoothness = percentSmoothness,
                startX = totalMinX / 2,
                startY = 0f,
                endX = totalMaxX,
                endY = height
            )
            lineTo(x = totalMaxX, y = height)
            // path bottom back
            lineTo(x = totalMinX, y = height)
            endSmoothLineTo(
                percentSmoothness = percentSmoothness,
                startX = totalMinX,
                totalMinX / 2,
                endY = 0f
            )
            close()
        }
    }
}

fun RoaDuration.toFullTimeline(): FullTimeline? {
    val fullOnset = onset?.toFullDurationRange()
    val fullComeup = comeup?.toFullDurationRange()
    val fullPeak = peak?.toFullDurationRange()
    val fullOffset = offset?.toFullDurationRange()
    return if (fullOnset != null && fullComeup != null && fullPeak != null && fullOffset != null) {
        FullTimeline(
            onset = fullOnset,
            comeup = fullComeup,
            peak = fullPeak,
            offset = fullOffset
        )
    } else {
        null
    }
}

fun RoaDuration.toTotalTimeline(): TotalTimeline? {
    val fullTotal = total?.toFullDurationRange()
    return if (fullTotal != null) {
        TotalTimeline(total = fullTotal)
    } else {
        null
    }
}

fun DurationRange.toFullDurationRange(): FullDurationRange? {
    return if (min != null && max != null) {
        FullDurationRange(min, max)
    } else {
        null
    }
}

fun Path.startSmoothLineTo(
    percentSmoothness: Float,
    startX: Float,
    startY: Float,
    endX: Float,
    endY: Float
) {
    val diff = endX - startX
    val controlX = startX + (diff * percentSmoothness)
    quadraticBezierTo(controlX, startY, endX, endY)
}

fun Path.endSmoothLineTo(
    percentSmoothness: Float,
    startX: Float,
    endX: Float,
    endY: Float
) {
    val diff = endX - startX
    val controlX = endX - (diff * percentSmoothness)
    quadraticBezierTo(controlX, endY, endX, endY)
}