# Entity 생성자 접근 제한

## AS-IS

JPA Entity 작성 시 다음과 같이 생성자의 접근 범위를 제한해서 사용하고 있다.

```java
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
```

- 생성자의 접근 범위를 제한하는 이유
- 제한하지 않았을 때와 제한했을 때의 차이
- JPA Entity 생성자의 접근 제한과 동작
- JPA Entity 생성자 구성 기준:JPA Entity에서 생성자가 필요한 이유


## 학습

### 1. 생성자의 접근 범위를 제한하는 이유

생성자를 제한하지 않으면 외부 코드에서 엔티티의 생성자를 자유롭게 호출할 수 있다.

이 경우 개발자가 의도하지 않은 생성 방법으로 객체를 생성하거나, 필요한 값이 설정되지 않은 불완전한 상태의 엔티티를 생성할 수 있다.

따라서 생성자의 접근 범위를 제한하고 외부에는 개발자가 의도한 생성자, 정적 팩토리 메서드, Builder 등의 생성 방법만 제공하여 엔티티의 생성 경로를 제어할 수 있다.


### 2. 제한하지 않았을 때와 제한했을 때의 차이

생성자를 `public`으로 공개하면 다른 패키지에서도 해당 생성자를 자유롭게 호출할 수 있다.

반면 `protected`, `private` 등의 접근 제어자를 사용하면 생성자를 호출할 수 있는 범위를 제한할 수 있다.

#### 접근 제어자별 접근 범위

- `public`: 모든 패키지에서 생성자 호출 가능
- `protected`: 같은 패키지 또는 상속 관계에서 접근 가능
- `private`: 해당 클래스 내부에서만 접근 가능


### 3. JPA Entity 생성자의 접근 제한과 동작

#### NoArgsConstructor - PROTECTED

JPA Entity에는 매개변수가 없는 기본 생성자가 필요하다.

JPA 명세에서는 Entity 클래스에 `public` 또는 `protected` 기본 생성자를 제공하도록 요구한다.

```java
@NoArgsConstructor(access = AccessLevel.PROTECTED)
```

public과 protected 모두 JPA의 요구사항을 만족하지만, public으로 선언하면 외부 패키지에서도 다음과 같이 빈 Entity를 생성할 수 있다.

```java
Member member = new Member();
```

일반 애플리케이션 코드에서 빈 Entity를 임의로 생성할 필요는 없으므로, JPA가 사용할 수 있으면서 외부 패키지에서의 직접 호출은 제한할 수 있는 protected를 사용한다.

#### NoArgsConstructor - PRIVATE

기본 생성자를 `AccessLevel.PRIVATE`으로 설정하여 테스트하였다.

JPA 명세의 요구사항에는 맞지 않으므로 오류가 발생할 것이라 생각했지만 Entity의 저장 및 조회가 정상적으로 동작하였다.

일반적인 Entity 조회에서는 Proxy 객체가 반드시 필요한 것은 아니며, Hibernate는 `private` 기본 생성자에도 접근하여 실제 Entity 인스턴스를 생성할 수 있기 때문이다.

하지만 `EntityManager.getReference()`를 사용하여 Proxy 객체 생성을 요청했을 때는 `org.hibernate.HibernateException`이 발생하였다.

`getReference()`는 실제 Entity를 즉시 조회하는 대신 해당 Entity를 참조할 수 있는 Proxy 객체를 사용할 수 있도록 하는 JPA API이다.

Hibernate가 Proxy를 생성하는 과정에서는 Entity의 생성자 접근성이 영향을 미칠 수 있으며, 테스트에서는 `private` 기본 생성자로 인해 Proxy 생성에 실패하였다.

이를 통해 실제 Entity를 직접 생성하는 경우에는 Hibernate 구현에 의해 `private` 기본 생성자가 동작할 수 있지만, Proxy가 필요한 상황에서는 문제가 발생할 수 있음을 확인하였다.

따라서 특정 Hibernate 구현에서 `private` 기본 생성자가 동작하는 것에 의존하지 않고, JPA 명세를 준수하면서 외부의 불필요한 객체 생성을 제한할 수 있는 `protected` 기본 생성자를 사용한다.

#### AllArgsConstructor - PRIVATE

AllArgsConstructor는 JPA가 요구하는 생성자가 아니다.

모든 필드를 받는 생성자를 public으로 공개하면 외부에서 ID를 포함한 모든 필드를 임의로 지정하여 Entity를 생성할 수 있다.

따라서 모든 필드를 받는 생성자는 private으로 제한하고, 외부에는 개발자가 의도한 객체 생성 방법만 제공한다.


### 4. JPA Entity 생성자 구성 기준:JPA Entity에서 생성자가 필요한 이유

앞선 학습을 통해 JPA Entity의 기본 생성자는 JPA Provider가 실제 Entity 및 Proxy 객체를 생성하기 위해 필요하며, JPA 명세에서는 `public` 또는 `protected` 접근 범위를 요구한다는 것을 확인하였다.

따라서 기본 생성자는 JPA의 요구사항을 만족하면서 외부에서 빈 Entity를 직접 생성하는 것을 제한하기 위해 `protected`로 선언한다.

반면 모든 필드를 받는 생성자는 JPA가 요구하는 생성자가 아니므로 외부에서 임의로 사용하는 것을 방지하기 위해 `private`으로 제한한다.

```java
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
```

## TO-BE
JPA Entity의 생성자는 다음 기준으로 작성한다.

- JPA가 Entity 및 Proxy 객체를 생성할 수 있도록 기본 생성자를 제공한다.
- 기본 생성자는 JPA 명세를 준수하면서 외부에서 빈 Entity를 직접 생성하는 것을 제한하기 위해 `protected`로 선언한다.
- 모든 필드를 받는 생성자는 JPA가 요구하는 생성자가 아니며, 외부에서 임의로 사용하는 것을 방지하기 위해 `private`으로 제한한다.
- 외부에는 개발자가 의도한 생성자, 정적 팩토리 메서드, Builder 등의 생성 방법만 제공한다.

```java
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
```

