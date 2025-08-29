import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
클래스명: BankApp
기능: 콘솔 메뉴를 통해 계좌 개설, 입금, 출금, 잔액 조회 기능
작성 날짜: 2025/08/02
*/
public class BankApp {
  
   /*
  클래스명: Account
  기능: 계좌번호와 잔액을 관리
  작성 날짜: 2025/08/02
  */
    static class Account {
        private final String id;
        private BigDecimal balance = BigDecimal.ZERO;

        Account(String id) {
            this.id = id;
        }

        public String getId() { return id; }
        public BigDecimal getBalance() { return balance; }

        public void deposit(BigDecimal amount) {
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("금액은 0보다 커야 합니다.");
            }
            balance = balance.add(amount);
        }

        public void withdraw(BigDecimal amount) {
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("금액은 0보다 커야 합니다.");
            }
            if (balance.compareTo(amount) < 0) {
                throw new IllegalStateException("잔액 부족");
            }
            balance = balance.subtract(amount);
        }
    }
  
   /*
  클래스명: Bank
  기능: 여러 개의 계좌를 생성하고 관리하는 클래스
  작성 날짜: 2025/08/02
  */
    static class Bank {
        private final Map<String, Account> accounts = new HashMap<>();

        public void createAccount(String id) {
            if (accounts.containsKey(id)) {
                throw new IllegalArgumentException("이미 존재하는 계좌");
            }
            accounts.put(id, new Account(id));
        }

        public Account getAccount(String id) {
            Account acc = accounts.get(id);
            if (acc == null) throw new IllegalArgumentException("계좌 없음");
            return acc;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bank bank = new Bank();

        while (true) {
            System.out.println("\n1. 계좌개설 2. 입금 3. 출금 4. 조회 5. 종료");
            System.out.print("선택: ");
            String menu = sc.nextLine();

            try {
                switch (menu) {
                    case "1" -> {
                        System.out.print("계좌번호: ");
                        String id = sc.nextLine();
                        bank.createAccount(id);
                        System.out.println("계좌 생성됨: " + id);
                    }
                    case "2" -> {
                        System.out.print("계좌번호: ");
                        String id = sc.nextLine();
                        Account acc = bank.getAccount(id);
                        System.out.print("입금액: ");
                        BigDecimal amt = new BigDecimal(sc.nextLine());
                        acc.deposit(amt);
                        System.out.println("현재 잔액: " + acc.getBalance());
                    }
                    case "3" -> {
                        System.out.print("계좌번호: ");
                        String id = sc.nextLine();
                        Account acc = bank.getAccount(id);
                        System.out.print("출금액: ");
                        BigDecimal amt = new BigDecimal(sc.nextLine());
                        acc.withdraw(amt);
                        System.out.println("현재 잔액: " + acc.getBalance());
                    }
                    case "4" -> {
                        System.out.print("계좌번호: ");
                        String id = sc.nextLine();
                        Account acc = bank.getAccount(id);
                        System.out.println("현재 잔액: " + acc.getBalance());
                    }
                    case "5" -> {
                        System.out.println("프로그램 종료");
                        return;
                    }
                    default -> System.out.println("잘못된 입력");
                }
            } catch (Exception e) {
                System.out.println("[오류] " + e.getMessage());
            }
        }
    }
}
