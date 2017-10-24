package com.demo.adnetwork.util;

import org.springframework.util.Assert;

public class StringUtils
{
  public static String checkNotBlank(final String value)
  {
    Assert.notNull(value);
    Assert.hasText(value, "Value must not be empty!");
    return value;
  }

  public static String checkNotBlank(final String value, String errorMessage)
  {
    Assert.notNull(value, errorMessage);
    Assert.hasText(value, "Value must not be empty!");
    return value;
  }

  public boolean isBlank(final String text)
  {
    return !org.springframework.util.StringUtils.hasText(text);
  }
}
