package io.opentelemetry.kotlin.tracing

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.attributes.AnyValue
import io.opentelemetry.kotlin.attributes.AttributesMutator

/**
 * A [Span] that wraps a [SpanContext] and cannot record any data. This is useful for propagating
 * traces where it's not necessary to mutate the span - e.g. if a caller only needs to know the
 * trace/span IDs for a parent span.
 *
 * https://opentelemetry.io/docs/specs/otel/trace/api/#wrapping-a-spancontext-in-a-span
 */
@OptIn(ExperimentalApi::class)
internal class NonRecordingSpanImpl(
    override val spanContext: SpanContext,
) : Span {

    override val parent: SpanContext = createInvalidSpanContext()

    override fun setName(name: String) {
    }

    override fun setStatus(status: StatusData) {
    }

    override fun end() {
    }

    override fun end(timestamp: Long) {
    }

    override fun isRecording(): Boolean = false

    override fun addLink(
        spanContext: SpanContext,
        attributes: (AttributesMutator.() -> Unit)?
    ) {
    }

    override fun addEvent(
        name: String,
        timestamp: Long?,
        attributes: (AttributesMutator.() -> Unit)?
    ) {
    }

    override fun setStringAttribute(key: String, value: String) {
    }

    override fun setBooleanAttribute(key: String, value: Boolean) {
    }

    override fun setLongAttribute(key: String, value: Long) {
    }

    override fun setDoubleAttribute(key: String, value: Double) {
    }

    override fun setBooleanListAttribute(key: String, value: List<Boolean>) {
    }

    override fun setStringListAttribute(key: String, value: List<String>) {
    }

    override fun setLongListAttribute(key: String, value: List<Long>) {
    }

    override fun setDoubleListAttribute(key: String, value: List<Double>) {
    }

    override fun setByteArrayAttribute(key: String, value: ByteArray) {
    }

    override fun setAnyValueAttribute(key: String, value: AnyValue) {
    }
}
