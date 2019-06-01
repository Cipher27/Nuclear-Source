package com.vision.rs.io;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Extends the Genesis API RSBuffer, and adds obfuscated data-types.
 * 
 * @author Teemu Uusitalo.
 * 
 */
public class RSBuffer {

	/**
	 * Bit masks used for writing bits into a byte RSBuffer.
	 */
	public static int[] BITMASK = new int[32];

	static {
		for (int i = 0; i < 32; i++) {
			BITMASK[i] = (1 << i) - 1;
		}
	}
	private int bitPosition;

	/**
	 * Our RSBuffer.
	 */
	protected byte[] buffer;
	/**
	 * Our position.
	 */
	protected int position = 0;

	/**
	 * Creates a new RSBuffer object with specified length.
	 * 
	 * @param length
	 *            the specified length.
	 */
	public RSBuffer(int length) {
		buffer = new byte[length];
	}

	/**
	 * Increments the position with the given amount.
	 * 
	 * @param amt
	 *            the amount to add to the position.
	 */
	public void addPosition(int amt) {
		this.position += amt;
	}

	/**
	 * Creates a new RSBuffer object with specified data.
	 * 
	 * @param data
	 *            the specified data.
	 */
	public RSBuffer(byte[] data) {
		this.buffer = data;
	}

	/**
	 * Expand the RSBuffer with specified length.
	 * 
	 * @param length
	 *            the length of bytes required at least to expand.
	 */
	private void expand(int length) {
		int newCap = buffer.length * 2;
		if (newCap < 0)
			newCap = Integer.MAX_VALUE;
		byte[] newRSBuffer = new byte[newCap];
		try {
			while (position > buffer.length) {
				position--;
			}
			System.arraycopy(buffer, 0, newRSBuffer, 0, position);
		} catch (Exception e) {

		}
		buffer = newRSBuffer;
	}

	/**
	 * Check if the RSBuffer has space for amount of bytes.
	 * 
	 * @param minimumCapacity
	 *            the amount of bytes.
	 * @return if it has space for the given amount of bytes.
	 */
	private boolean afford(int minimumCapacity) {
		return position + minimumCapacity < buffer.length;
	}

	/**
	 * Puts the specified byte to the RSBuffer.
	 * 
	 * @param b
	 *            the byte to put.
	 * @return this object, for chaining.
	 */
	public RSBuffer put(int b) {
		if (!afford(1)) {
			expand(1);
		}
		buffer[position++] = (byte) b;
		return this;
	}

	/**
	 * Puts the specified byte to the RSBuffer.
	 * 
	 * @param b
	 *            the byte to put.
	 * @return this object, for chaining.
	 */
	public RSBuffer putU(int b) {
		if (!afford(1)) {
			expand(1);
		}
		buffer[position++] = (byte) (b & 0xff);
		return this;
	}

	/**
	 * Puts the specified short to the RSBuffer.
	 * 
	 * @param s
	 *            the short to put.
	 * @return this object, for chaining.
	 */
	public RSBuffer putShort(int s) {
		put((byte) (s >> 8));
		put((byte) s);
		return this;
	}

	/**
	 * Puts a fixed-value short into the buffer.
	 * 
	 * @param value
	 *            the short to put.
	 */
	public void putFixedShort(int value) {
		putShort(value < 0 ? value + 65536 : value);
	}

	/**
	 * Puts the specified short to the RSBuffer.
	 * 
	 * @param s
	 *            the short to put.
	 * @return this object, for chaining.
	 */
	public RSBuffer putUShort(int s) {
		put((byte) (s >> 8));
		put((byte) (s & 0xff));
		return this;
	}

	/**
	 * Puts the specified 24-bit int to the RSBuffer.
	 * 
	 * @param i
	 *            the 24-bit int to put.
	 * @return this object, for chaining.
	 */
	public RSBuffer put24BitInt(int i) {
		put((byte) (i >> 16));
		put((byte) (i >> 8));
		put((byte) i);
		return this;
	}

	/**
	 * Puts the specified 24-bit int to the RSBuffer.
	 * 
	 * @param i
	 *            the 24-bit int to put.
	 * @return this object, for chaining.
	 */
	public RSBuffer putLETri(int i) {
		put((byte) i);
		put((byte) (i >> 8));
		put((byte) (i >> 16));
		return this;
	}

	/**
	 * Puts the specified 24-bit int to the RSBuffer.
	 * 
	 * @param i
	 *            the 24-bit int to put.
	 * @return this object, for chaining.
	 */
	public RSBuffer put24BitUInt(int i) {
		put((byte) ((i & 0xff) >> 16));
		put((byte) ((i & 0xff) >> 8));
		put((byte) (i & 0xff));
		return this;
	}

	/**
	 * Puts the specified integer to the RSBuffer.
	 * 
	 * @param i
	 *            the integer to put.
	 * @return this object, for chaining.
	 */
	public RSBuffer putInt(int i) {
		put((byte) (i >> 24));
		put((byte) (i >> 16));
		put((byte) (i >> 8));
		put((byte) i);
		return this;
	}

	/**
	 * Puts the specified integer to the RSBuffer.
	 * 
	 * @param i
	 *            the integer to put.
	 * @return this object, for chaining.
	 */
	public RSBuffer putUInt(int i) {
		put((byte) ((i & 0xff) >> 24));
		put((byte) ((i & 0xff) >> 16));
		put((byte) ((i & 0xff) >> 8));
		put((byte) (i & 0xff));
		return this;
	}

	/**
	 * Puts the specified integer to the RSBuffer.
	 * 
	 * @param l
	 *            the integer to put.
	 * @return this object, for chaining.
	 */
	public RSBuffer putLong(long l) {
		put((byte) (l >> 56));
		put((byte) (l >> 48));
		put((byte) (l >> 40));
		put((byte) (l >> 32));
		put((byte) (l >> 24));
		put((byte) (l >> 16));
		put((byte) (l >> 8));
		put((byte) l);
		return this;
	}

	/**
	 * Puts the specified integer to the RSBuffer.
	 * 
	 * @param l
	 *            the integer to put.
	 * @return this object, for chaining.
	 */
	public RSBuffer putULong(long l) {
		put((byte) ((l & 0xff) >> 56));
		put((byte) ((l & 0xff) >> 48));
		put((byte) ((l & 0xff) >> 40));
		put((byte) ((l & 0xff) >> 32));
		put((byte) ((l & 0xff) >> 24));
		put((byte) ((l & 0xff) >> 16));
		put((byte) ((l & 0xff) >> 8));
		put((byte) (l & 0xff));
		return this;
	}

	/**
	 * @return a byte from the RSBuffer from incremented position.
	 */
	public int get() {
		return buffer[position++];
	}

	/**
	 * @return a byte from the RSBuffer from incremented position.
	 */
	public int getU() {
		return (buffer[position++] & 0xff);
	}

	public byte getS() {
		return (byte) (128 - buffer[position++]);
	}

	/**
	 * @return a 16-bit Short.
	 */
	public int getShort() {
		return (short) ((get() << 8) + get());
	}

	/**
	 * @return a 16-bit Short.
	 */
	public int getUShort() {
		return (((get() & 0xff) << 8) + (get() & 0xff));
	}

	/**
	 * @return an 24-bit integer.
	 */
	public int get24BitInt() {
		return ((get() << 16) + (get() << 8) + get());
	}

	/**
	 * @return an 24-bit integer.
	 */
	public int get24BitUInt() {
		return (((get() & 0xff) << 16) + ((get() & 0xff) << 8) + (get() & 0xff));
	}

	/**
	 * @return an integer.
	 */
	public int getInt() {
		return ((get() << 24) + (get() << 16) + (get() << 8) + get());
	}

	/**
	 * @return an integer.
	 */
	public int getUInt() {
		return (((get() & 0xff) << 24) + ((get() & 0xff) << 16)
				+ ((get() & 0xff) << 8) + (get() & 0xff));
	}

	/**
	 * @return a long.
	 */
	public long getLong() {
		long l = getInt();
		long l1 = getInt();
		return (l << 32) + l1;
	}

	/**
	 * @return a long.
	 */
	public long getULong() {
		long l = getUInt() & 0xffffffffL;
		long l1 = getUInt() & 0xffffffffL;
		return (l << 32) + l1;
	}

	/**
	 * Reads a string.
	 * 
	 * @return the read string.
	 */
	public String getString() {
		String s = "";
		byte b;
		while ((b = (byte) getU()) != 0) {
			s += (char) b;
		}
		return s;
	}

	/**
	 * Reads a string.
	 * 
	 * @return the read string.
	 */
	public String getJagString() {
		String s = "";
		byte b;
		while ((b = (byte) getU()) != 0) {
			s += (char) b;
		}
		return s;
	}

	/**
	 * Reads a string that's terminated by the number 10.
	 * 
	 * @return
	 */
	public String getTenString() {
		String s = "";
		byte b;
		while ((b = (byte) getU()) != 10) {

			s += (char) b;
		}
		return s;
	}

	/**
	 * Puts a string.
	 * 
	 * @param string
	 *            the string to put.
	 */
	public RSBuffer putString(String string) {
		for (byte b : string.getBytes()) {
			put(b);
		}
		put((byte) 0);
		return this;
	}

	/**
	 * Puts a string.
	 * 
	 * @param string
	 *            the string to put.
	 */
	public RSBuffer putJString(String string) {
		put((byte) 0);
		for (byte b : string.getBytes()) {
			put(b);
		}
		put((byte) 0);
		return this;
	}

	/**
	 * Puts a string.
	 * 
	 * @param string
	 *            the string to put.
	 */
	public void putTenString(String string) {
		for (byte b : string.getBytes()) {
			put(b);
		}
		put((byte) 10);
	}

	/**
	 * Puts a JagexString 2. TODO: special characters.
	 * 
	 * @param string
	 *            the string to put.
	 */
	public void putJagString2(String string) {
		put((byte) 0);
		for (byte b : string.getBytes()) {
			put(b);
		}
		put((byte) 0);
	}

	/**
	 * @return the current RSBuffer.
	 */
	public byte[] toArray() {
		return buffer;
	}

	/**
	 * @return the current position
	 */
	public int position() {
		return position;
	}

	/**
	 * Decipher the RSBuffer using 128-bit xTea deciphering.
	 * 
	 * @param keys
	 *            the keys used to decipher.
	 * @param startPosition
	 *            the start position.
	 * @param endPosition
	 *            the end position.
	 */
	public void xTeaDecrypt(int[] keys, int startPosition, int endPosition) {
		int backPosition = position;
		position = startPosition;
		int operationCount = (endPosition - startPosition) / 8;
		for (int i = 0; i < operationCount; i++) {
			int firstInt = getUInt();
			int secondInt = getUInt();
			int modifier1 = 0xc6ef3720;
			int modifier2 = 0x9e3779b9;
			for (int round = 32; round-- > 0;) {
				secondInt -= (firstInt >>> 5 ^ firstInt << 4) + firstInt
						^ modifier1 + keys[modifier1 >>> 11 & 0x4c600003];
				modifier1 -= modifier2;
				firstInt -= secondInt + (secondInt << 4 ^ secondInt >>> 5)
						^ keys[3 & modifier1] + modifier1;
			}
			position -= 8;
			putInt(firstInt);
			putInt(secondInt);
		}
		position = backPosition;
	}

	/**
	 * Set the current position.
	 * 
	 * @param position
	 *            the new position.
	 */
	public void position(int position) {
		this.position = position;
	}

	/**
	 * @return a ByteRSBuffer from this Genesis RSBuffers data.
	 */
	public ByteBuffer toByteBuffer() {
		return ByteBuffer.wrap(buffer, 0, position);
	}

	/**
	 * Writes a genesis RSBuffer to this genesis RSBuffer.
	 */
	public int write(RSBuffer src) {
		int count = 0;
		for (byte b : src.toArray()) {
			put(b);
			count++;
		}
		return count;
	}

	/**
	 * Writes an byteRSBuffer to this genesis RSBuffer.
	 */
	public int write(ByteBuffer src) {
		int count = 0;
		while (src.hasRemaining()) {
			put(src.get());
			count++;
		}
		return count;
	}

	/**
	 * Returns a new RSBuffer object with a payload the size of the current
	 * position and the data will be the RSBuffer of this object. Also signs the
	 * packet length.
	 * 
	 * @return the resulting RSBuffer.
	 */
	public RSBuffer flip() {
		byte[] resultData = new byte[position];
		System.arraycopy(buffer, 0, resultData, 0, position);
		return new RSBuffer(resultData);
	}

	public void initBitWriting() {
		bitPosition = position * 8;
	}

	public void finishBitWriting() {
		position = (bitPosition + 7) / 8;
	}

	public void putBit(boolean bool) {
		putBits(1, bool ? 1 : 0);
	}

	public void addBit(int bit, int pos) {
		if (pos >= buffer.length) {
			expand(buffer.length * 8);
		}
		buffer[pos] &= ~bit;
	}

	public void placeBit(int bit, int pos) {
		if (pos >= buffer.length) {
			expand(buffer.length * 8);
		}
		buffer[pos] |= bit;
	}

	public void putBits(int numBits, int value) {
		int bytePos = bitPosition >> 3;
		int bitOffset = 8 - (bitPosition & 7);
		bitPosition += numBits;
		for (; numBits > bitOffset; bitOffset = 8) {
			addBit(BITMASK[bitOffset], bytePos);
			placeBit(value >> numBits - bitOffset & BITMASK[bitOffset],
					bytePos++);
			numBits -= bitOffset;
		}
		if (numBits == bitOffset) {
			addBit(BITMASK[bitOffset], bytePos);
			placeBit(value & BITMASK[bitOffset], bytePos);
		} else {
			addBit(BITMASK[numBits] << bitOffset - numBits, bytePos);
			placeBit((value & BITMASK[numBits]) << bitOffset - numBits, bytePos);
		}
	}

	/**
	 * Encrypt this RSBuffer, and return the result.
	 * 
	 * @param exponent
	 *            the exponent used
	 * @param m
	 *            the modulus used.
	 * @return the crypted result RSBuffer.
	 */
	public RSBuffer applyRSA(BigInteger exponent, BigInteger m) {
		byte[] data = toArray();
		BigInteger crypted = new BigInteger(data).modPow(exponent, m);
		data = crypted.toByteArray();
		RSBuffer result = new RSBuffer(data.length);
		result.put(data);
		result.position(0);
		return result;
	}

	public void put(byte[] data) {
		for (int i = 0; i < data.length; i++) {
			put(data[i]);
		}
	}

	public int remaining() {
		return buffer.length - position;
	}

	public void putSmart(int i) {
		if (i < 128) {
			put((byte) i);
		} else {
			putShort((short) i);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bitPosition;
		result = prime * result + Arrays.hashCode(buffer);
		result = prime * result + position;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		RSBuffer other = (RSBuffer) obj;
		if (!Arrays.equals(buffer, other.buffer)) {
			return false;
		}
		if (position != other.position) {
			return false;
		}
		return true;
	}

	public byte[] getBytes(int length) {
		byte[] bytes = new byte[length];
		for (int i = 0; i < length; i++) {
			bytes[i] = buffer[position++];
		}
		return bytes;
	}

	public byte[] getUBytes(int length) {
		byte[] bytes = new byte[length];
		for (int i = 0; i < length; i++) {
			bytes[i] = (byte) (buffer[position++] & 0xff);
		}
		return bytes;
	}

	public RSBuffer putShortA(int i) {
		put((byte) (i >> 8));
		put((byte) (i + 128));
		return this;
	}

	public RSBuffer putLEShortA(int i) {
		put((byte) (i + 128));
		put((byte) (i >> 8));
		return this;
	}

	public RSBuffer putC(int i) {
		put((byte) -i);
		return this;
	}

	public RSBuffer putA(int i) {
		put((byte) (i + 128));
		return this;
	}

	public void putInt1(int i) {
		put(i >> 8);
		put(i);
		put(i >> 24);
		put(i >> 16);
	}

	public RSBuffer putInt2(int i) {
		put(i >> 16);
		put(i >> 24);
		put(i);
		put(i >> 8);
		return this;
	}

	public RSBuffer putLEShort(int i) {
		put((byte) (i));
		put((byte) (i >> 8));
		return this;
	}

	public int getByteC() {
		return -get();
	}

	public int getLEUShort() {
		int i = getU() + (getU() << 8);
		if (i > 32767) {
			i -= 0x10000;
		}
		return (short) i;
	}

	public int getUnsignedLEShortA() {
		return (get() - 128 & 0xff) + (getU() << 8);
	}

	public int getUShortA() {
		return (getU() << 8) + ((get() - 128) & 0xff);
	}

	public int getInt1() {
		int i = (getU() << 8);
		i += getU();
		i += (getU() << 24);
		i += (getU() << 16);
		return i;
	}

	public int getA() {
		return (byte) (get() - 128);
	}

	public void putS(int i) {
		put(i - 128);
	}

	public void putCS(int i) {
		put(128 - i);
	}

	public int getSmart() {
		int i = 0xff & get();
		if (i >= 128)
			return -32768 + ((i << 8) + getU());
		return i;
	}

	public void putAC(int i) {
		put(-i + 128);
	}

	public void putLEInt(int i) {
		put(i);
		put(i >> 8);
		put(i >> 16);
		put(i >> 24);

	}
}
