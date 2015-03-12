package com.bumptech.glide.load;

import java.security.MessageDigest;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * A set of {@link Option Options} to apply to in memory and disk cache keys.
 */
public final class Options implements Key {
  private final SortedMap<Option<?>, Object> values = new TreeMap<>();

  public void putAll(Options other) {
    values.putAll(other.values);
  }

  public <T> Options set(Option<T> option, T value) {
    values.put(option, value);
    return this;
  }

  @SuppressWarnings("unchecked")
  public <T> T get(Option<T> option) {
    return values.containsKey(option) ? (T) values.get(option) : option.getDefaultValue();
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Options) {
      Options other = (Options) o;
      return values.equals(other.values);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return values.hashCode();
  }

  @Override
  public void updateDiskCacheKey(MessageDigest messageDigest) {
    for (Map.Entry<Option<?>, Object> entry : values.entrySet()) {
      updateDiskCacheKey(entry.getKey(), entry.getValue(), messageDigest);
    }
  }

  @Override
  public String toString() {
    return "Options{"
        + "values=" + values
        + '}';
  }

  @SuppressWarnings("unchecked")
  private static <T> void updateDiskCacheKey(Option<T> option, Object value, MessageDigest md) {
    option.update((T) value, md);
  }
}
