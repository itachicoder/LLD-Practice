package pu.com.ay.DesignPatterns.StructuralPatterns.Facade;

// Complex subsystem classes

class VideoFile {
    private String filename;
    private String format;

    public VideoFile(String filename) {
        this.filename = filename;
        this.format = filename.substring(filename.lastIndexOf('.') + 1);
    }

    public String getFilename() {
        return filename;
    }

    public String getFormat() {
        return format;
    }
}

interface Codec {}

class MPEG4CompressionCodec implements Codec {
    public String toString() {
        return "MPEG4 Compression Codec";
    }
}

class OggCompressionCodec implements Codec {
    public String toString() {
        return "Ogg Compression Codec";
    }
}

class CodecFactory {
    public static Codec extract(VideoFile file) {
        if ("mp4".equalsIgnoreCase(file.getFormat())) {
            System.out.println("CodecFactory: Extracting MPEG4 codec...");
            return new MPEG4CompressionCodec();
        } else if ("ogg".equalsIgnoreCase(file.getFormat())) {
            System.out.println("CodecFactory: Extracting Ogg codec...");
            return new OggCompressionCodec();
        } else {
            throw new IllegalArgumentException("Unsupported format");
        }
    }
}

class BitrateReader {
    public static String read(String filename, Codec codec) {
        System.out.println("BitrateReader: Reading file '" + filename + "' with " + codec + " codec...");
        return "Data read from " + filename;
    }

    public static String convert(String data, Codec codec) {
        System.out.println("BitrateReader: Converting data with " + codec + " codec...");
        return "Converted data with " + codec;
    }
}

class AudioMixer {
    public static String fix(String data) {
        System.out.println("AudioMixer: Fixing audio...");
        return "Fixed audio for " + data;
    }
}

// Facade class

class VideoConversionFacade {
    public String convertVideo(String filename, String format) {
        System.out.println("VideoConversionFacade: Converting '" + filename + "' to '" + format + "' format...");
        VideoFile file = new VideoFile(filename);

        // Extracting codec
        Codec sourceCodec = CodecFactory.extract(file);

        // Selecting destination codec
        Codec destinationCodec = switch (format) {
            case "mp4" -> new MPEG4CompressionCodec();
            case "ogg" -> new OggCompressionCodec();
            default -> throw new IllegalArgumentException("Unsupported format");
        };

        // Conversion process
        String data = BitrateReader.read(file.getFilename(), sourceCodec);
        String convertedData = BitrateReader.convert(data, destinationCodec);
        String finalData = AudioMixer.fix(convertedData);

        System.out.println("VideoConversionFacade: Conversion completed.");
        return file.getFilename().replace("." + file.getFormat(), "") + "_converted." + format;
    }
}

// Client code

public class Main {
    public static void main(String[] args) {
        VideoConversionFacade converter = new VideoConversionFacade();
        String output = converter.convertVideo("example_video.ogg", "mp4");
        System.out.println("Output file: " + output);
    }
}

