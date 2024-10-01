package com.alexmprog.common.utils.platfrom

import com.alexmprog.common.utils.platform.Platform
import com.alexmprog.common.utils.platform.PlatformType
import platform.UIKit.UIDevice

actual val platform: Platform = Platform(
    type = PlatformType.Ios,
    name = UIDevice.currentDevice.systemName(),
    version = UIDevice.currentDevice.systemVersion
)
