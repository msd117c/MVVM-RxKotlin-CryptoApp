package crypto.msd117c.com.cryptocurrency.utils

import org.mockito.Mockito

fun <T> any(type: Class<T>): T = Mockito.any<T>(type)