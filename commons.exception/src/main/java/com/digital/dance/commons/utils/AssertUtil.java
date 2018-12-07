package com.digital.dance.commons.utils;

import com.digital.dance.commons.exception.BizException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public class AssertUtil
{
  public static void isTrue(boolean expression, String message)
  {
    if (!expression)
      throw new BizException(message);
  }

  public static void isTrue(boolean expression)
  {
    isTrue(expression, "[Assertion failed] - this expression must be true");
  }

  public static void isNotNull(Object object, String message)
  {
    if (object == null)
      throw new BizException(message);
  }

  public static void isNotNull(Object object)
  {
    isNotNull(object, "[Assertion failed] - this argument is required; it must not be null");
  }

  public static void hasLength(String text, String message)
  {
    if (StringUtils.isEmpty(text))
      throw new BizException(message);
  }

  public static void hasLength(String text)
  {
    hasLength(text, "[Assertion failed] - this String argument must have length; it must not be null or empty");
  }

  public static void hasText(String text, String message)
  {
    if (StringUtils.isBlank(text))
      throw new BizException(message);
  }

  public static void hasText(String text)
  {
    hasText(text, "[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
  }

  public static void doesNotContain(String textToSearch, String substring, String message)
  {
    if (StringUtils.contains(textToSearch, substring))
      throw new BizException(message);
  }

  public static void doesNotContain(String textToSearch, String substring)
  {
    doesNotContain(textToSearch, substring, new StringBuilder().append("[Assertion failed] - this String argument must not contain the substring [").append(substring).append("]").toString());
  }

  public static void notEmpty(Object[] array, String message)
  {
    if (ArrayUtils.isEmpty(array))
      throw new BizException(message);
  }

  public static void notEmpty(Object[] array)
  {
    notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
  }

  public static void noNullElements(Object[] array, String message)
  {
    if (array != null)
      for (int i = 0; i < array.length; i++)
        if (array[i] == null)
          throw new BizException(message);
  }

  public static void noNullElements(Object[] array)
  {
    noNullElements(array, "[Assertion failed] - this array must not contain any null elements");
  }

  public static <E> void notEmpty(Collection<E> collection, String message)
  {
    isNotNull(collection, message);
    if (collection.isEmpty())
      throw new BizException(message);
  }

  public static <E> void notEmpty(Collection<E> collection)
  {
    notEmpty(collection, "[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
  }

  public static <K, V> void notEmpty(Map<K, V> map, String message)
  {
    isNotNull(map, message);
    if (map.isEmpty())
      throw new BizException(message);
  }

  public static <K, V> void notEmpty(Map<K, V> map)
  {
    notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
  }

  public static void isInstanceOf(Class<?> clazz, Object obj)
  {
    isInstanceOf(clazz, obj, "");
  }

  public static void isInstanceOf(Class<?> type, Object obj, String message)
  {
    isNotNull(type, "Type to check against must not be null");
    if (!type.isInstance(obj))
      throw new BizException(new StringBuilder().append(message).append(" Object of class [").append(obj != null ? obj.getClass().getName() : "null").append("] must be an instance of ").append(type).toString());
  }

  public static void isAssignable(Class<?> superType, Class<?> subType)
  {
    isAssignable(superType, subType, "");
  }

  public static void isAssignable(Class<?> superType, Class<?> subType, String message) {
    isNotNull(superType, "Type to check against must not be null");
    if ((subType == null) || (!superType.isAssignableFrom(subType)))
      throw new BizException(new StringBuilder().append(message).append(subType).append(" is not assignable to ").append(superType).toString());
  }

  public static void state(boolean expression, String message)
  {
    if (!expression)
      throw new IllegalStateException(message);
  }

  public static void state(boolean expression)
  {
    state(expression, "[Assertion failed] - this state invariant must be true");
  }

  public static void assertMaxLength(String str, int maxLength, String message)
  {
    if ((StringUtils.isEmpty(str)) || (str.length() > maxLength))
      throw new BizException(message);
  }

  public static void assertNonnegativeInt(int n, String errorMessage)
  {
    if (n < 0)
      throw new BizException(errorMessage);
  }

  public static void assertGreaterThanOrEqual(int a, int b, String errorMessage)
  {
    assertGreaterThanOrEqual(Integer.valueOf(a), Integer.valueOf(b), errorMessage);
  }

  public static void assertGreaterThanOrEqual(Integer a, Integer b, String errorMessage)
  {
    isNotNull(a);
    isNotNull(b);
    if (a.intValue() < b.intValue())
      throw new BizException(errorMessage);
  }

  public static void assertEqual(long l, long m, String errorMessage)
  {
    assertEqual(Long.valueOf(l), Long.valueOf(m), errorMessage);
  }

  public static void assertEqual(Long l, Long m, String errorMessage)
  {
    isNotNull(l);
    isNotNull(m);
    if (!l.equals(m))
      throw new BizException(errorMessage);
  }

  public static void assertGreaterThanOrEqual(long a, long b, String errorMessage)
  {
    assertGreaterThanOrEqual(Long.valueOf(a), Long.valueOf(b), errorMessage);
  }

  public static void assertGreaterThanOrEqual(Long a, Long b, String errorMessage)
  {
    isNotNull(a);
    isNotNull(b);
    if (a.longValue() < b.longValue())
      throw new BizException(errorMessage);
  }

  public static void assertGreaterThan(long a, long b, String errorMessage)
  {
    if (a <= b)
      throw new BizException(errorMessage);
  }

  public static void assertGreaterThan(Long a, Long b, String errorMessage)
  {
    isNotNull(a);
    isNotNull(b);
    assertGreaterThan(a.longValue(), b.longValue(), errorMessage);
  }

  public static void assertOneOfThem(short a, String errorMessage, short[] values)
  {
    boolean pass = false;
    if (values != null) {
      for (short n : values)
        if (a == n) {
          pass = true;
          break;
        }
    }
    else {
      throw new BizException("values为空");
    }
    if (!pass)
      throw new BizException(errorMessage);
  }

  public static void assertGreaterThanZero(long l, String errorMessage)
  {
    assertGreaterThanZero(Long.valueOf(l), errorMessage);
  }

  public static void assertGreaterThanZero(Long l, String errorMessage)
  {
    isNotNull(l, errorMessage);
    if (l.longValue() < 1L)
      throw new BizException(errorMessage);
  }

  public static void assertOneOfThemThanZero(String errorMessage, int[] values)
  {
    boolean exception = true;
    if (values != null) {
      for (int n : values) {
        if (n >= 0) {
          exception = false;
          break;
        }
      }
    }
    if (exception)
      throw new BizException(errorMessage);
  }

  public static void assertNotNull(Object o, String errorMessage)
  {
    if (o == null)
      throw new BizException(errorMessage);
  }

  public static void assertTimeRange(Date start, Date end, String errorMessage)
  {
    if ((start == null) || (end == null)) {
      throw new BizException("start或end为空");
    }
    if (start.after(end))
      throw new BizException(errorMessage);
  }
}
