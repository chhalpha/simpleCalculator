package dao;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "knowledgebase")
public class KnowledgeBase implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "expression")
    private String expression;

    @Column(name = "result")
    private Double result;


    public KnowledgeBase(){}


    public KnowledgeBase(String expression){
        this.expression = expression;
    }

    public KnowledgeBase(String expression, Double result){
        this.expression = expression;
        this.result = result;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }
}
