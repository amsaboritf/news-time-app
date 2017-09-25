package hello.util;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

/**
 * Created by amsaborit on 07/07/2017.
 */
public class JSONData implements Serializable {
    private boolean success;
    private long length;
    private List<? extends Serializable> data;

    /**
     * @param lenght Cantidad de elementos
     * @param data   Elementos a retornar
     */
    public JSONData(long lenght, List<? extends Serializable> data) {
        this.length = lenght;
        this.data = data;
        this.success = true;
    }
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<? extends Serializable> getData() {
        return data;
    }

    public void setData(List<? extends Serializable> data) {
        this.data = data;
    }
}