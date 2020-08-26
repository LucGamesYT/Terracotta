package org.terracotta.network;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketCodec;
import com.nukkitx.protocol.bedrock.v408.Bedrock_v408;
import io.netty.buffer.ByteBuf;

/**
 * Copyright (c) 2020, TerracottaMC and Kaooot
 * <p>
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @author Kaooot
 * @version 1.0
 */
public class ProtocolInfo {

    private static final BedrockPacketCodec currentCodec = Bedrock_v408.V408_CODEC;

    /**
     * Retrieves the current supported protocol version
     *
     * @return a fresh integer with the current protocol
     */
    public static int getProtocolVersion() {
        return ProtocolInfo.currentCodec.getProtocolVersion();
    }

    /**
     * Returns the current supported minecraft version
     *
     * @return a fresh String with the current version
     */
    public static String getMinecraftVersion() {
        return ProtocolInfo.currentCodec.getMinecraftVersion();
    }

    /**
     * Gets the current packet codec
     *
     * @return an {@link BedrockPacketCodec} object
     */
    public static BedrockPacketCodec getPacketCodec() {
        return ProtocolInfo.currentCodec;
    }

    /**
     * Tries to decode a packet with the given {@link ByteBuf} and the given id
     *
     * @param byteBuf bytes to read
     * @param id      packet id
     *
     * @return the decoded {@link BedrockPacket}
     */
    public static BedrockPacket tryDecodePacket(final ByteBuf byteBuf, final int id) {
        return ProtocolInfo.currentCodec.tryDecode(byteBuf, id);
    }

    /**
     * Tries to encode a {@link BedrockPacket} with the given {@link ByteBuf}
     *
     * @param byteBuf bytes to write
     * @param packet  packet object that contains data which should be encoded
     */
    public static void tryEncodePacket(final ByteBuf byteBuf, final BedrockPacket packet) {
        ProtocolInfo.currentCodec.tryEncode(byteBuf, packet);
    }
}