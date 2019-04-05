package com.theapache64.twinkill.network.utils.retrofit.interceptors

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset

/**
 * Created by theapache64 on 14/2/18.
 */

class CurlInterceptor : Interceptor {

	private var curlCommandBuilder: StringBuffer? = null

	private val UTF8 = Charset.forName("UTF-8")
	private var tag: String? = null

	constructor() {}

	constructor(tag: String) {
		this.tag = tag
	}


	@Throws(IOException::class)
	override fun intercept(chain: Interceptor.Chain): Response {

		val request = chain.request()

		curlCommandBuilder = StringBuffer("")
		// add cURL command
		curlCommandBuilder!!.append("curl ")
		curlCommandBuilder!!.append("-X ")
		// add method
		curlCommandBuilder!!.append(request.method().toUpperCase()).append(" ")
		// adding headers
		for (headerName in request.headers().names()) {
			addHeader(headerName, request.headers().get(headerName))
		}

		// adding request body
		val requestBody = request.body()
		if (request.body() != null) {
			val buffer = Buffer()
			assert(requestBody != null)
			requestBody!!.writeTo(buffer)
			val charset: Charset?
			val contentType = requestBody.contentType()
			if (contentType != null) {
				addHeader("Content-Type", request.body()!!.contentType()!!.toString())
				charset = contentType.charset(UTF8)
				assert(charset != null)
				curlCommandBuilder!!.append(" -d '").append(buffer.readString(charset!!))
					.append("'")
			}
		}

		// add request URL
		curlCommandBuilder!!.append(" \"").append(request.url().toString()).append("\"")
		curlCommandBuilder!!.append(" -L | jq '.'")

        CurlPrinter.print(tag, request.url().toString(), curlCommandBuilder!!.toString())
		return chain.proceed(request)
	}


	private fun addHeader(headerName: String, headerValue: String?) {
		curlCommandBuilder!!.append("-H " + "\"").append(headerName).append(": ")
			.append(headerValue).append("\" ")
	}

	object CurlPrinter {

		/**
		 * Drawing toolbox
		 */
		private val SINGLE_DIVIDER = "────────────────────────────────────────────"

		private var sTag = "CURL"

		internal fun print(tag: String?, url: String, msg: String) {
			// setting tag if not null
			if (tag != null)
				sTag = tag

			val logMsg = "\n" + "\n" +
					"URL: " + url +
					"\n" +
					SINGLE_DIVIDER +
					"\n" +
					msg +
					" " +
					" \n" +
					SINGLE_DIVIDER +
					" \n "
			log(logMsg)
		}

		private fun log(msg: String) {
			Log.d(sTag, msg)
		}
	}
}