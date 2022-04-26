package Bean;

import com.google.gson.annotations.SerializedName;

public class Jiqiren_Bean {


    private int status;
    private String msg;
    private ResultEntity result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultEntity getResult() {
        return result;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public static class ResultEntity {
        private String type;
        private String content;
        private RelquestionEntity relquestion;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public RelquestionEntity getRelquestion() {
            return relquestion;
        }

        public void setRelquestion(RelquestionEntity relquestion) {
            this.relquestion = relquestion;
        }

        public static class RelquestionEntity {
            @SerializedName("0")
            private String _$0;

            public String get_$0() {
                return _$0;
            }

            public void set_$0(String _$0) {
                this._$0 = _$0;
            }
        }
    }
}
