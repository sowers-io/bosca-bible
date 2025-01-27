package io.bosca.bible.processor

import java.util.*

object XMLProcessor {

    private fun flatten(map: MutableMap<String, Any>) {
        for (entry in map.entries) {
            when (entry.value) {
                is MutableMap<*, *> -> {
                    @Suppress("UNCHECKED_CAST")
                    val m = entry.value as MutableMap<String, Any>
                    if (m.size == 1 && m.keys.first() == "#text") {
                        entry.setValue(m.entries.first().value)
                    } else {
                        flatten(m)
                    }
                }
                is List<*> -> {
                    @Suppress("UNCHECKED_CAST")
                    val l = entry.value as MutableList<Any>
                    for (i in l.indices) {
                        val v = l[i]
                        if (v is MutableMap<*, *>) {
                            @Suppress("UNCHECKED_CAST")
                            val m = v as MutableMap<String, Any>
                            if (m.size == 1 && m.keys.first() == "#text") {
                                l[i] = m.entries.first().value
                            } else {
                                flatten(m)
                            }
                        }
                    }
                }
            }
        }
    }

    fun process(data: ByteArray): Map<String, Any> {
        val parserFactory = javax.xml.parsers.SAXParserFactory.newInstance()
        val saxParser = parserFactory.newSAXParser()
        val root = mutableMapOf<String, Any>()
        val stack = Stack<MutableMap<String, Any>>()
        stack.push(root)
        val handler = object : org.xml.sax.helpers.DefaultHandler() {
            override fun startElement(
                uri: String?,
                localName: String?,
                qName: String?,
                attrs: org.xml.sax.Attributes?
            ) {
                val obj = mutableMapOf<String, Any>()
                attrs?.let { a ->
                    for (i in 0 until a.length) {
                        obj[a.getQName(i)] = a.getValue(i)
                    }
                }
                val current = stack.peek()
                if (current.containsKey(qName)) {
                    val c = current[qName]
                    if (c is List<*>) {
                        @Suppress("UNCHECKED_CAST")
                        (c as MutableList<Any>).add(obj)
                    } else {
                        current[qName!!] = mutableListOf(c, obj)
                    }
                } else {
                    current[qName!!] = obj
                }
                stack.push(obj)
            }
            override fun characters(ch: CharArray?, start: Int, length: Int) {
                stack.peek()["#text"] = String(ch ?: CharArray(0), start, length)
            }
            override fun endElement(uri: String?, localName: String?, qName: String?) {
                stack.pop()
            }
        }
        saxParser.parse(data.inputStream(), handler)
        flatten(root)
        return root
    }
}