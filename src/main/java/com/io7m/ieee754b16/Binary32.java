/*
 * Copyright © 2013 <code@io7m.com> http://io7m.com
 * 
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.io7m.ieee754b16;

final class Binary32
{
  static final int NEGATIVE_ZERO_BITS;
  static final int MASK_SIGN;
  static final int MASK_EXPONENT;
  static final int MASK_SIGNIFICAND;
  static final int BIAS;

  static {
    NEGATIVE_ZERO_BITS = 0x80000000;
    MASK_SIGN = 0x80000000;
    MASK_EXPONENT = 0x7ff00000;
    MASK_SIGNIFICAND = 0x7fffff;
    BIAS = 127;
  }

  /**
   * <p>
   * Extract and unbias the exponent of the given packed <code>float</code>
   * value.
   * </p>
   * <p>
   * The exponent is encoded <i>biased</i> as a number in the range
   * <code>[0, 255]</code>, with <code>0</code> indicating that the number is
   * <i>subnormal</i> and <code>[1, 254]</code> denoting the actual exponent
   * plus {@link #BIAS}. Infinite and <code>NaN</code> values always have a
   * biased exponent of <code>255</code>.
   * </p>
   * <p>
   * This function will therefore return:
   * </p>
   * <ul>
   * <li>
   * <code>0 - {@link #BIAS} = -127</code> iff the input is a <i>subnormal</i>
   * number.</li>
   * <li>An integer in the range
   * <code>[1 - {@link #BIAS}, 254 - {@link #BIAS}] = [-126, 127]</code> iff
   * the input is a <i>normal</i> number.</li>
   * <li>
   * <code>255 - {@link #BIAS} = 128</code> iff the input is
   * {@link #POSITIVE_INFINITY}, {@link #NEGATIVE_INFINITY}, or
   * <code>NaN</code>.</li>
   * </ul>
   * 
   * @see #packSetExponentUnbiasedUnchecked(int)
   */

  static int unpackGetExponentUnbiased(
    final float d)
  {
    final int b = Float.floatToRawIntBits(d);
    final int em = b & Binary32.MASK_EXPONENT;
    final int es = em >> 23;
    return es - Binary32.BIAS;
  }

  static int unpackGetSign(
    final float d)
  {
    final int b = Float.floatToRawIntBits(d);
    return ((b & Binary32.MASK_SIGN) >> 31) & 1;
  }

  static int unpackGetSignificand(
    final float d)
  {
    final int b = Float.floatToRawIntBits(d);
    return b & Binary32.MASK_SIGNIFICAND;
  }
}
