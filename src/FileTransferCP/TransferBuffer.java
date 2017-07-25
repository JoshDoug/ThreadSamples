package FileTransferCP;

public class TransferBuffer {
    private byte[] buffer;
    private int length;

    public void setBuffer(byte[] buffer, int length) {
        this.buffer = buffer;
        this.length = length;
    }

    public byte[] getBuffer() {
        return this.buffer;
    }

    public int getLength() {
        return this.length;
    }
}
