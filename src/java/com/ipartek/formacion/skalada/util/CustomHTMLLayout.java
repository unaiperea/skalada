package com.ipartek.formacion.skalada.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

/**
 * This HTML Log Formatter is a simple replacement for the standard Log4J
 * HTMLLayout formatter and replaces the default timestamp (milliseconds,
 * relative to the start of the log) with a more readable timestamp (an example
 * of the default format is 2008-11-21-18:35:21.472-0800).
 * @author Curso
 * */

public class CustomHTMLLayout extends org.apache.log4j.HTMLLayout {

	// RegEx pattern looks for <tr> <td> nnn...nnn </td> (all whitespace
	// ignored)

	private static final String RXTIMESTAMP = "\\s*<\\s*tr\\s*>\\s*<\\s*td\\s*>\\s*(\\d*)\\s*<\\s*/td\\s*>";

	/*
	 * The timestamp format. The format can be overriden by including the
	 * following property in the Log4J configuration file:
	 * 
	 * log4j.appender.<category>.layout.TimestampFormat
	 * 
	 * using the same format string as would be specified with SimpleDateFormat.
	 */

	private String timestampFormat = "yyyy-MM-dd HH:mm:ss"; // Default
	// 2008-11-21-18:35:21.472-0800

	private SimpleDateFormat sdf = new SimpleDateFormat(this.timestampFormat);

	/**
	 * Constructor
	 */
	public CustomHTMLLayout() {
		super();
	}

	@Override()
	public String getHeader() {
		StringBuffer sbuf = new StringBuffer();
		sbuf.append("<head>" + Layout.LINE_SEP);
		
		sbuf.append("</head>" + Layout.LINE_SEP);
		
		sbuf.append("<body>" + Layout.LINE_SEP);
		sbuf.append("<hr size=\"1\" noshade>" + Layout.LINE_SEP);
		sbuf.append("Log session start time " + new java.util.Date() + "<br>"
				+ Layout.LINE_SEP);
		sbuf.append("<br>" + Layout.LINE_SEP);
		sbuf.append("<table id=\"table-logs\" cellspacing=\"0\" cellpadding=\"4\" border=\"1\" bordercolor=\"#224466\" width=\"100%\">"
				+ Layout.LINE_SEP);
		sbuf.append("<tr>" + Layout.LINE_SEP);
		sbuf.append("<th>Time</th>" + Layout.LINE_SEP);
		sbuf.append("<th>Thread</th>" + Layout.LINE_SEP);
		sbuf.append("<th>Level</th>" + Layout.LINE_SEP);
		sbuf.append("<th>Category</th>" + Layout.LINE_SEP);
		sbuf.append("<th>File:Line</th>" + Layout.LINE_SEP);
		sbuf.append("<th>Message</th>" + Layout.LINE_SEP);
		sbuf.append("</tr>" + Layout.LINE_SEP);
		return sbuf.toString();
		// return super.getHeader();
	}

	/** Override HTMLLayout's format() method */
	/**
	 * @param event Evento
	 */

	public String format(LoggingEvent event) {
		String record = super.format(event); // Get the log record in the
												// default HTMLLayout format.

		Pattern pattern = Pattern.compile(RXTIMESTAMP); // RegEx to find the
														// default timestamp
		Matcher matcher = pattern.matcher(record);

		// If default timestamp cannot be found,
		if (!matcher.find()) {
			return record; // Just return the unmodified log record.
		}

		StringBuffer buffer = new StringBuffer(record);

		buffer.replace(matcher.start(1), // Replace the default timestamp with
											// one formatted as desired.
		matcher.end(1), this.sdf.format(new Date(event.timeStamp)));

		return buffer.toString(); // Return the log record with the desired
									// timestamp format.
	}

	@Override()
	public String getFooter() {
		StringBuffer sbuf = new StringBuffer();
		//librerias JavaScript
		sbuf.append("<script src=\"https://code.jquery.com/jquery-1.11.3.min.js\"><script> " + Layout.LINE_SEP);
		sbuf.append("<script src=\"https://cdn.datatables.net/1.10.9/js/jquery.dataTables.min.js\"><script> " + Layout.LINE_SEP);
		sbuf.append("<script>    </script>" + Layout.LINE_SEP);
				
		sbuf.append("</table>" + Layout.LINE_SEP);
		sbuf.append("</body>" + Layout.LINE_SEP);
		return sbuf.toString();
		// return super.getFooter();
	}

}