package salemby.com.github.literalura.services;

public interface IConvertData {

    <T> T obtainData(String json, Class<T> clazz);

}
