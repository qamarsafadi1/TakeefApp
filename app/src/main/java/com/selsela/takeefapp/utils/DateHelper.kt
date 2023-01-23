package com.selsela.takeefapp.utils

import android.annotation.SuppressLint
import android.util.Log
import com.selsela.takeefapp.utils.Extensions.Companion.log
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

class DateHelper {
    companion object {
        fun getLast30Dates(): List<Date> {
            val dates = arrayListOf<Date>()
            for (i in 0..30) {
                dates.add(Date(Date().time.plus(i * 24 * 60 * 60 * 1000)))
            }
            return dates
        }

        fun findHoursBetween(startTime: String, endTime: String, duration: Int): ArrayList<String> {
            val hh_mm = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
            val h_mm_a = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
            val times = arrayListOf<String>()
            try {
                val time1 = hh_mm.parse(startTime) ?: Date()
                val time2 = hh_mm.parse(endTime) ?: Date()
                if (time2.time < time1.time) {
                    time2.time = time2.time.plus(24 * 60 * 60 * 1000)
                }
                val cal = Calendar.getInstance()
                cal.time = time1
                while (cal.time.time < time2.time) {
                    // add cal time to list
                    times.add(h_mm_a.format(cal.time))
                    cal.timeInMillis = cal.timeInMillis.plus(duration * 60 * 1000)

                }

            } catch (ex: java.lang.Exception) {
                ex.printStackTrace()
            }
            times.log("timess")
            return times

        }

        fun getTodayFormatedDate(): String {
            val ddd = SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH)
            try {
                Calendar.getInstance().time.log("Date()")
                return ddd.format(Date())
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                return ""
            }
        }

        fun formatDate(createdAt: String): String {
            val h_mm_ss = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val ddd = SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH)
            try {
                val d1 = h_mm_ss.parse(createdAt)
                d1?.let {
                    return ddd.format(d1)
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                return ""
            }
            return ""
        }

        fun formatDate(time: Long): String {
            val ddd = SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH)
            try {
                return ddd.format(Date(time))
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                return ""
            }
        }

        fun formatTime(time: Long): String {
            val ddd = SimpleDateFormat("HH:mm", Locale.ENGLISH)
            try {
                return ddd.format(Date(time))
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                return ""
            }
        }

        fun formatDateForServer(date: Date): String {
            val h_mm_ss = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            try {
                return h_mm_ss.format(date)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                return ""
            }
        }

        fun getDateOnly(createdAt: String): String {
            val h_mm_ss = SimpleDateFormat("yyyy-MM-dd HH:mm:ss a", Locale.ENGLISH)
            val day = SimpleDateFormat("dd-MM-yyyy ", Locale.ENGLISH)
            try {
                val d1 = h_mm_ss.parse(createdAt)
                d1?.let {
                    return day.format(d1)
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                return ""
            }
            return ""
        }

        fun getDateOnly2(createdAt: String): String {
            val h_mm_ss = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
            val day = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
            try {
                val d1 = h_mm_ss.parse(createdAt)
                d1?.let {
                    return day.format(d1)
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                return ""
            }
            return ""
        }

        fun getOrderDate(createdAt: String): List<String> {
            var list = listOf<String>()
            val h_mm_ss = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.ENGLISH)
            val year = SimpleDateFormat("yyyy", Locale.ENGLISH)
            val day = SimpleDateFormat("dd", Locale.ENGLISH)
            val month = SimpleDateFormat("MM", Locale.ENGLISH)
            val hh = SimpleDateFormat("hh", Locale.ENGLISH)
            val mm = SimpleDateFormat("mm", Locale.ENGLISH)
            val a = SimpleDateFormat("a")
            try {
                val d1 = h_mm_ss.parse(createdAt)
                d1?.let {
                   val yearInt = year.format(d1)
                   val dayInt = day.format(d1)
                   val monthInt = month.format(d1)
                   val hour = hh.format(d1)
                   val min = mm.format(d1)
                   val a = a.format(d1)
                    return listOf(yearInt,dayInt,monthInt,hour,min,a)
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                return list
            }
            return list
        }

        fun getTimeOnly(createdAt: String): String {
            val h_mm_ss = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.ENGLISH)
            val day = SimpleDateFormat("HH:mm a", Locale.ENGLISH)
            try {
                val d1 = h_mm_ss.parse(createdAt)
                d1?.let {
                    return day.format(d1)
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                return ""
            }
            return ""
        }

        fun getTimeAmPm(createdAt: String): String {
            val h_mm_ss = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
            val day = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
            try {
                val d1 = h_mm_ss.parse(createdAt)
                d1?.let {
                    return day.format(d1)
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                return ""
            }
            return ""
        }

        fun getTimeOnly2(createdAt: String): String {
            val h_mm_ss = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
            val day = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
            try {
                val d1 = h_mm_ss.parse(createdAt)
                d1?.let {
                    return day.format(d1)
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                return ""
            }
            return ""
        }

        fun getDayAbvFromDate(date: Date): String {

            val day = SimpleDateFormat("E", Locale.ENGLISH)
            try {
                return day.format(date)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                return ""
            }
        }

        fun getDayNumFromDate(date: Date): String {
            val day = SimpleDateFormat("dd", Locale.ENGLISH)
            try {
                return day.format(date)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                return ""
            }
        }

        fun getDayIdFromCalendar(date: Date): Int {
            val cal = Calendar.getInstance()
            cal.time = date

            return cal.get(Calendar.DAY_OF_WEEK)

        }

        fun get12To24(createdAt: String): String {
            val h_mm_a = SimpleDateFormat("hh:mm", Locale.ENGLISH)
            val hh_mm = SimpleDateFormat("HH:mm", Locale.ENGLISH)
            try {
                val d1 = h_mm_a.parse(createdAt)
                d1?.let {
                    return hh_mm.format(d1)
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                return ""
            }
            return ""
        }

        fun getAmPm(createdAt: String): String {
            val h_mm_a = SimpleDateFormat("HH:mm", Locale.ENGLISH)
            val hh_mm = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
            try {
                val d1 = h_mm_a.parse(createdAt)
                d1?.let {
                    return hh_mm.format(d1)
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                return ""
            }
            return ""
        }

        fun get24To12(createdAt: String): String {
            val hh_mm = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
            val h_mm_a = SimpleDateFormat("hh:mm", Locale.ENGLISH)
            val a = SimpleDateFormat("a")
            try {
                val d1 = hh_mm.parse(createdAt)
                d1?.let {
                    return h_mm_a.format(d1) + " " + a.format(d1)
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                return ""
            }
            return ""
        }

        fun isDateTimeLater(dateTxt: String, timeTxt: String): Boolean {
            val dateTime = SimpleDateFormat("yyyy-MM-ddHH:mm:ss", Locale.ENGLISH)
            val currentDate = Date()

            try {
                val d1 = dateTime.parse("$dateTxt$timeTxt")
                d1?.let {

                    return currentDate.time >= d1.time
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                return false
            }
            return false
        }

        fun getDateTimeTxt(dateTxt: String, timeTxt: String): String {
            val dateTime = SimpleDateFormat("yyyy-MM-ddHH:mm:ss", Locale.ENGLISH)
            val day = SimpleDateFormat("EEEE", Locale(LocalData.appLocal))
            val date = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            val time = SimpleDateFormat("hh:mm", Locale.ENGLISH)
            val a = SimpleDateFormat("a", Locale(LocalData.appLocal))
            try {
                val d1 = dateTime.parse("$dateTxt$timeTxt")
                d1?.let {
                    val resDay = day.format(d1)
                    val resDate = date.format(d1)
                    val resTime = time.format(d1)
                    val resA = a.format(d1)
                    return "$resDay,$resDate, $resTime $resA"
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                return ""
            }
            return ""
        }

        fun getDate(dateTxt: String): String {
            val dateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
            val date = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            try {
                val d1 = dateTime.parse(dateTxt)
                d1?.let {
                    val resDate = date.format(d1)
                    return resDate
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                return ""
            }
            return ""
        }

        fun getTimeStampToDate(time: Long): String {
            val dateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
            try {

                val resDate = dateTime.format(time)
                return resDate

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                return ""
            }
        }

        fun remainTimeFunction(endDate: String): List<Long> {
            try {
                val dates = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
                val date1 = Date()
                val date2: Date = dates.parse(endDate) ?: Date()
                var difference = 0L
                if (date1.time > date2.time)
                    difference = date1.time - date2.time
                else
                    difference = date2.time - date1.time

                val seconds = difference / 1000
                val minutes = seconds / 60
                val hours = minutes / 60
                val days = hours / 24

                Log.e("TAG_5", "CurrentDate is : $date1")
                Log.e("TAG_5", "Final date is : $date2")
                Log.e("TAG_5", "Day Difference: $days")
                Log.e("TAG_5", "hours Difference: ${hours % 24}")
                Log.e("TAG_5", "Minute Difference: ${minutes % 60}")
                Log.e("TAG_5", "Seconds Difference: ${seconds % 60}")
                return listOf(days, (hours % 24), (minutes % 60), (seconds % 60))

            } catch (exception: Exception) {
                Log.e("TAG_5", "exception $exception")
                return listOf()

            }
        }



        fun remainTimeFunctionFull(endDate: String): List<Long> {
            try {
                val dates = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.ENGLISH)
                val date1 = Date()
                val date2: Date = dates.parse(endDate) ?: Date()
                var difference = 0L
                if (date1.time > date2.time)
                    difference = date1.time - date2.time
                else
                    difference = date2.time - date1.time

                val seconds = difference / 1000
                val minutes = seconds / 60
                val hours = minutes / 60
                val days = hours / 24

                Log.e("TAG_5", "CurrentDate is : $date1")
                Log.e("TAG_5", "Final date is : $date2")
                Log.e("TAG_5", "Day Difference: $days")
                Log.e("TAG_5", "hours Difference: ${hours % 24}")
                Log.e("TAG_5", "Minute Difference: ${minutes % 60}")
                Log.e("TAG_5", "Seconds Difference: ${seconds % 60}")
                return listOf(days, (hours % 24), (minutes % 60), (seconds % 60))

            } catch (exception: Exception) {
                Log.e("TAG_5", "exception $exception")
                return listOf()

            }
        }


        @SuppressLint("SimpleDateFormat")
        fun formatSpecificDate(date: String, time: String): String {
            val h_mm_a = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
            val dd = SimpleDateFormat("EEEE")
            val hh_mm = SimpleDateFormat("dd/MM/yyyy, hh:mm", Locale.ENGLISH)
            val a = SimpleDateFormat("aa")
            try {
                return dd.format(h_mm_a.parse(date + " " + time) ?: Date()) + ", " + hh_mm.format(
                    h_mm_a.parse(
                        date + " " + time
                    ) ?: Date()
                ) + " " + a.format(h_mm_a.parse(date + " " + time) ?: Date())
            } catch (ex: java.lang.Exception) {
                ex.printStackTrace()
                return ""
            }

        }


        fun getFormattedDate(date: String): Date? {
            val readFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
            val writeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
            var date1: Date? = null
            date1 = readFormat.parse(date);
            val formattedDate = writeFormat.format(date1)
            formattedDate.log("formattedDateformattedDate")
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
            val startDate = dateFormat.parse(formattedDate)
            "${startDate} , ${Date()}".log("startDatestartDatestartDate")
            return startDate
        }

        fun isShallStart(date: String): Boolean {
            val startDate = getFormattedDate(date)
            val todayDate = Date()
            return todayDate.date >= startDate!!.date
        }

        fun isShallStartCourse(date: String): Boolean {
            val startDate = getFormattedDate(date)
            val todayDate = Date()
            return todayDate.date == startDate!!.date
        }
    }

}