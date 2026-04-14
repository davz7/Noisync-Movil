package mx.edu.noisync.core.session

import java.util.concurrent.atomic.AtomicBoolean
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object SessionExpirationManager {
    private val expirationDispatched = AtomicBoolean(false)
    private val _events = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val events = _events.asSharedFlow()

    fun notifySessionExpired() {
        if (expirationDispatched.compareAndSet(false, true)) {
            _events.tryEmit(Unit)
        }
    }

    fun reset() {
        expirationDispatched.set(false)
    }
}
