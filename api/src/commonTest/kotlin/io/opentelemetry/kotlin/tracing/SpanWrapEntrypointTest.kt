package io.opentelemetry.kotlin.tracing

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.attributes.AnyValue
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotSame
import kotlin.test.assertSame

@OptIn(ExperimentalApi::class)
internal class SpanWrapEntrypointTest {

    private val spanContext = createSpanContext("12345678901234567890123456789012", "1234567890123456") {
        isSampled = true
        isRemote = true
    }

    @Test
    fun testWrapValidSpanContext() {
        val span = spanContext.wrapInSpan()
        assertSame(spanContext, span.spanContext)
        assertSame(createInvalidSpanContext(), span.parent)
        assertFalse(span.isRecording())
    }

    @Test
    fun testWrapInvalidSpanContext() {
        val span = createInvalidSpanContext().wrapInSpan()
        assertSame(createInvalidSpanContext(), span.spanContext)
        assertSame(createInvalidSpanContext(), span.parent)
        assertFalse(span.isRecording())
        assertSame(span, createInvalidSpanContext().wrapInSpan())
    }

    @Test
    fun testWrapReturnsNewInstanceForValidSpanContext() {
        assertNotSame(spanContext.wrapInSpan(), spanContext.wrapInSpan())
    }

    @Test
    fun testMutationsIgnored() {
        val span = spanContext.wrapInSpan()
        span.setName("name")
        span.setStatus(StatusData.Error("err"))
        span.setStringAttribute("key", "value")
        span.setBooleanAttribute("key", true)
        span.setLongAttribute("key", 1)
        span.setDoubleAttribute("key", 1.0)
        span.setStringListAttribute("key", listOf("value"))
        span.setBooleanListAttribute("key", listOf(true))
        span.setLongListAttribute("key", listOf(1))
        span.setDoubleListAttribute("key", listOf(1.0))
        span.setByteArrayAttribute("key", byteArrayOf(1))
        span.setAnyValueAttribute("key", AnyValue.StringValue("value"))
        span.addEvent("event", null) { setStringAttribute("key", "value") }
        span.addLink(createInvalidSpanContext()) { setStringAttribute("key", "value") }
        span.end()
        span.end(1000)

        assertSame(spanContext, span.spanContext)
        assertFalse(span.isRecording())
    }
}
