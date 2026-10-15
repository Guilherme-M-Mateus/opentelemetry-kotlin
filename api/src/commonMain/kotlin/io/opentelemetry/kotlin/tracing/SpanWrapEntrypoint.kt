package io.opentelemetry.kotlin.tracing

import io.opentelemetry.kotlin.ExperimentalApi

@OptIn(ExperimentalApi::class)
private val INVALID_SPAN: Span = NonRecordingSpanImpl(createInvalidSpanContext())

/**
 * Wraps this [SpanContext] in a non-recording [Span]. The returned [Span] exposes this
 * [SpanContext], has an invalid parent, and ignores all attempts to record data or end it.
 *
 * This is useful for propagating a [SpanContext] (e.g. one extracted from a remote request)
 * as the parent of new spans, or for storing it in a [io.opentelemetry.kotlin.context.Context].
 *
 * This does not require an [io.opentelemetry.kotlin.OpenTelemetry] instance.
 *
 * https://opentelemetry.io/docs/specs/otel/trace/api/#wrapping-a-spancontext-in-a-span
 */
@ExperimentalApi
public fun SpanContext.wrapInSpan(): Span = when (this) {
    createInvalidSpanContext() -> INVALID_SPAN
    else -> NonRecordingSpanImpl(this)
}
