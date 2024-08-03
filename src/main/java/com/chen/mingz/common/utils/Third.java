package com.chen.mingz.common.utils;

/**
 * Created by chenmingzhi on 18/5/25.
 */
public class Third<First, Second, Three> {

    private First first;

    private Second second;

    private Three three;


    public Third(First first, Second second, Three three) {
        this.first = first;
        this.second = second;
        this.three = three;
    }

    public static <T1, T2, T3> Third<T1, T2, T3> of(T1 first, T2 second, T3 three) {
        return new Third<>(first, second, three);
    }

    public First getFirst() {
        return first;
    }

    public void setFirst(First first) {
        this.first = first;
    }

    public Second getSecond() {
        return second;
    }

    public void setSecond(Second second) {
        this.second = second;
    }

    public Three getThree() {
        return three;
    }

    public void setThree(Three three) {
        this.three = three;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Third)) return false;

        Third<?, ?, ?> third = (Third<?, ?, ?>) o;

        if (first != null ? !first.equals(third.first) : third.first != null) return false;
        if (second != null ? !second.equals(third.second) : third.second != null) return false;
        return three != null ? three.equals(third.three) : third.three == null;
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        result = 31 * result + (three != null ? three.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Third{" +
                "first=" + first +
                ", second=" + second +
                ", three=" + three +
                '}';
    }
}
