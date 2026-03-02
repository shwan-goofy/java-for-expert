package org.example.chapter02_3;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Comparator;

/**
 * Lombok @ToString, @EqualsAndHashCode 사용 예시.
 * Comparable과 Comparator 비교.
 */
@ToString
@EqualsAndHashCode
public class Product implements Comparable<Product> {

    private final String name;
    private final int price;
    private final String category;

    public Product(String name, int price, String category) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("상품명은 비어 있을 수 없습니다");
        }
        if (price < 0) {
            throw new IllegalArgumentException("가격은 0 이상이어야 합니다: " + price);
        }
        this.name     = name;
        this.price    = price;
        this.category = category;
    }

    public String getName()     { return name;     }
    public int    getPrice()    { return price;    }
    public String getCategory() { return category; }

    // Comparable — 가격 오름차순을 자연 순서로 정의
    @Override
    public int compareTo(Product other) {
        return Integer.compare(this.price, other.price);
    }

    // ----------------------------------------------------------------
    // 정적 Comparator들 — 다양한 정렬 기준 제공
    // ----------------------------------------------------------------

    /** 이름 오름차순 */
    public static final Comparator<Product> BY_NAME =
            Comparator.comparing(Product::getName);

    /** 가격 내림차순 */
    public static final Comparator<Product> BY_PRICE_DESC =
            Comparator.comparingInt(Product::getPrice).reversed();

    /** 카테고리 → 가격 복합 정렬 */
    public static final Comparator<Product> BY_CATEGORY_THEN_PRICE =
            Comparator.comparing(Product::getCategory)
                      .thenComparingInt(Product::getPrice);
}
