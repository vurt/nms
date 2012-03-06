package me.vurt.nms.core.file.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 文件摘要工具类，根据{@link DigestUtils}修改而来，统一摘要接口，方便切换摘要算法，
 * 略微优化对大批量文件进行摘要计算时的性能
 * 
 * @author yanyl
 * 
 */
public class DigestTool {
	/**
	 * 摘要算法名称
	 * @author yanyl
	 *
	 */
	public interface DigestAlgorithm{
		String MD5="MD5";
		String SHA="SHA";
	}
	/**
	 * 创建摘要工具类实例
	 * @param algorithm 摘要算法名称
	 * @return
	 */
	public static DigestTool getInstance(String algorithm) {
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			return new DigestTool(digest);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	  /**
     * Calls {@link StringUtils#getBytesUtf8(String)}
     * 
     * @param data
     *            the String to encode
     * @return encoded bytes
     */
    private static byte[] getBytesUtf8(String data) {
        return StringUtils.getBytesUtf8(data);
    }

	private static final int STREAM_BUFFER_LENGTH = 1024;

	private MessageDigest digest;

	private DigestTool(MessageDigest digest) {
		this.digest = digest;
	}
	
	private MessageDigest getDigest(){
		return digest;
	}
	
	/**
     * Read through an InputStream and returns the digest for the data
     * 
     * @param digest
     *            The MessageDigest to use (e.g. MD5)
     * @param data
     *            Data to digest
     * @return MD5 digest
     * @throws IOException
     *             On error reading from the stream
     */
    private static byte[] digest(MessageDigest digest, InputStream data) throws IOException {
        byte[] buffer = new byte[STREAM_BUFFER_LENGTH];
        int read = data.read(buffer, 0, STREAM_BUFFER_LENGTH);

        while (read > -1) {
            digest.update(buffer, 0, read);
            read = data.read(buffer, 0, STREAM_BUFFER_LENGTH);
        }

        return digest.digest();
    }

    /**
     * Calculates the digest.
     * 
     * @param data
     *            Data to digest
     * @return digest
     */
    public byte[] digest(byte[] data) {
        return getDigest().digest(data);
    }

    /**
     * Calculates the digest.
     * 
     * @param data
     *            Data to digest
     * @return digest
     * @throws IOException
     *             On error reading from the stream
     * @since 1.4
     */
    public byte[] digest(InputStream data) throws IOException {
        return digest(getDigest(), data);
    }

    /**
     * Calculates the digest.
     * 
     * @param data
     *            Data to digest
     * @return digest
     */
    public byte[] digest(String data) {
        return digest(getBytesUtf8(data));
    }

    /**
     * Calculates the digest and returns the value as a hex string.
     * 
     * @param data
     *            Data to digest
     * @return digest as a hex string
     */
    public String digestHex(byte[] data) {
        return Hex.encodeHexString(digest(data));
    }

    /**
     * Calculates the digest and returns the value as a hex string.
     * 
     * @param data
     *            Data to digest
     * @return digest as a hex string
     * @throws IOException
     *             On error reading from the stream
     * @since 1.4
     */
    public String digestHex(InputStream data) throws IOException {
        return Hex.encodeHexString(digest(data));
    }

    /**
     * Calculates the digest and returns the value as a hex string.
     * 
     * @param data
     *            Data to digest
     * @return digest as a hex string
     */
    public String digestHex(String data) {
        return Hex.encodeHexString(digest(data));
    }
}
