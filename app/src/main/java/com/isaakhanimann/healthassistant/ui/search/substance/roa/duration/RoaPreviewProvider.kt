package com.isaakhanimann.healthassistant.ui.search.substance.roa.duration

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.isaakhanimann.healthassistant.data.substances.*

class RoaPreviewProvider : PreviewParameterProvider<Roa> {
    override val values: Sequence<Roa> = sequenceOf(
        Roa(
            route = AdministrationRoute.INSUFFLATED,
            roaDose = RoaDose(
                "mg",
                threshold = 20.0,
                light = DoseRange(
                    min = 20.0,
                    max = 40.0
                ),
                common = DoseRange(
                    min = 40.0,
                    max = 90.0
                ),
                strong = DoseRange(
                    min = 90.0,
                    max = 140.0
                ),
                heavy = 140.0
            ),
            roaDuration = RoaDuration(
                onset = DurationRange(
                    min = 20f,
                    max = 40f,
                    units = DurationUnits.MINUTES
                ),
                comeup = DurationRange(
                    min = 15f,
                    max = 30f,
                    units = DurationUnits.MINUTES
                ),
                peak = DurationRange(
                    min = 1.5f,
                    max = 2.5f,
                    units = DurationUnits.HOURS
                ),
                offset = DurationRange(
                    min = 1f,
                    max = 1.5f,
                    units = DurationUnits.HOURS
                ),
                total = DurationRange(
                    min = 3f,
                    max = 5f,
                    units = DurationUnits.HOURS
                ),
                afterglow = DurationRange(
                    min = 12f,
                    max = 48f,
                    units = DurationUnits.HOURS
                )
            ),
            bioavailability = Bioavailability(
                min = 70.0,
                max = 75.0
            )
        ),
        Roa(
            route = AdministrationRoute.INTRAVENOUS,
            roaDose = RoaDose(
                "mg",
                threshold = 20.0,
                light = DoseRange(
                    min = 20.0,
                    max = 40.0
                ),
                common = DoseRange(
                    min = 40.0,
                    max = 90.0
                ),
                strong = DoseRange(
                    min = 90.0,
                    max = 140.0
                ),
                heavy = 140.0
            ),
            roaDuration = RoaDuration(
                onset = DurationRange(
                    min = 20f,
                    max = 40f,
                    units = DurationUnits.MINUTES
                ),
                comeup = DurationRange(
                    min = 15f,
                    max = 30f,
                    units = DurationUnits.MINUTES
                ),
                peak = DurationRange(
                    min = 1.5f,
                    max = 2.5f,
                    units = DurationUnits.HOURS
                ),
                offset = DurationRange(
                    min = 1f,
                    max = 1.5f,
                    units = DurationUnits.HOURS
                ),
                total = DurationRange(
                    min = 3f,
                    max = 5f,
                    units = DurationUnits.HOURS
                ),
                afterglow = DurationRange(
                    min = 12f,
                    max = 48f,
                    units = DurationUnits.HOURS
                )
            ),
            bioavailability = Bioavailability(
                min = 70.0,
                max = 75.0
            )
        )
    )
}