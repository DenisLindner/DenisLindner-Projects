import java.util.*;

public class Main {
    static Scanner SC= new Scanner(System.in);
    static Map<String, String> senhas = new HashMap<>();
    static Map<String, Double> saldos = new HashMap<>();
    static final String CPFADMIN = "conf";
    static String SENHAADMIN = "conf";

    public static void main(String[] args){
        while (true) {
            System.out.println("Menu\n1 - Cadastro de Clientes\n2 - Login de Cliente\n3 - Admin\n4 - Sair\nEscolha:");
            int opcao = SC.nextInt();
            switch (opcao){
                case 1 -> cadastroCliente();
                case 2 -> loginCliente();
                case 3 -> loginAdimin();
                case 4 -> {
                    System.out.println("Saindo do Sistema...");
                    return;
                }
                default -> System.out.println("Entrada Inválida!");
            }
        }
    }

    public static void cadastroCliente(){
        String cpf;
        do {
            System.out.println("Insira o CPF do Cliente:");
            cpf = SC.next().trim();
            if (cpf.isEmpty()){ System.out.println("CPF inválido!"); }
            else if (saldos.containsKey(cpf)){
                System.out.println("CPF já cadastrado!");
                cpf = "";
            }
        } while (cpf.isEmpty());

        String senha;
        do {
            System.out.println("Insira a Senha do Cliente:");
            senha = SC.next().trim();
            if (senha.isEmpty()){ System.out.println("Senha Inválida!"); }
        } while (senha.isEmpty());

        senhas.put(cpf, senha);
        saldos.put(cpf, 0.0);
        System.out.println("Cliente Cadastrado com Sucesso!");
    }

    public static void loginCliente(){
        System.out.println("Insira o CPF do Cliente:");
        String cpf = SC.next().trim();
        if (!senhas.containsKey(cpf)){
            System.out.println("Cliente não Encontrado!");
            return;
        }

        System.out.println("Insira a Senha do Cliente:");
        String senha = SC.next().trim();
        if (!senha.equals(senhas.get(cpf))){
            System.out.println("Senha Incorreta!");
            return;
        }

        menuCliente(cpf);
    }

    public static void menuCliente(String cpf){
        while (true){
            System.out.println("Menu Cliente:\n1 - Ver Saldo\n2 - Depositar Valor\n3 - Sacar Valor\n4 - Transferir Valor\n5 - Sair da Conta\nEscolha:");
            int opcao = SC.nextInt();
            switch (opcao){
                case 1 -> verSaldo(cpf);
                case 2 -> depositarValor(cpf);
                case 3 -> sacarValor(cpf);
                case 4 -> transferirValor(cpf);
                case 5 -> {
                    return;
                }
                default -> System.out.println("Entrada Inválida!");
            }
        }
    }

    public static void verSaldo(String cpf){
        double saldo = saldos.get(cpf);
        System.out.printf("Saldo: R$%.2f%n", saldo);
    }

    public static void depositarValor(String cpf){
        System.out.println("Insira o valor de Depósito:");
        double valor = lerNumeroDouble();

        saldos.put(cpf, saldos.get(cpf)+valor);
        System.out.println("Depósito Efetuado com Sucesso!");
    }

    public static void sacarValor(String cpf){
        System.out.println("Insira o valor que deseja Sacar:");
        double valor = lerNumeroDouble();

        if (valor > saldos.get(cpf)){
            System.out.println("Saldo Insuficiente!");
            return;
        }

        saldos.put(cpf, saldos.get(cpf)-valor);
        System.out.println("Saque Efetuado com Sucesso!");
    }

    public static void transferirValor(String cpf){
        System.out.println("Insira o CPF do Cliente Destinatário:");
        String cpfDestinatario = SC.next().trim();

        if (!saldos.containsKey(cpfDestinatario)){
            System.out.println("Destinatário não Existente!");
            return;
        }

        if (cpfDestinatario.equals(cpf)){
            System.out.println("Impossível Transferir para sua própria Conta!");
            return;
        }

        System.out.println("Insira o valor do Depósito:");
        double valor = lerNumeroDouble();
        if (valor>saldos.get(cpf)){
            System.out.println("Saldo Insuficiente!");
            return;
        }

        saldos.put(cpf, saldos.get(cpf)-valor);
        saldos.put(cpfDestinatario, saldos.get(cpfDestinatario)+valor);
        System.out.println("Transferência Efetuada com Sucesso!");
    }

    public static void loginAdimin(){
        System.out.println("Insira o CPF do Admin:");
        String cpf = SC.next().trim();
        if (!cpf.equals(CPFADMIN)){
            System.out.println("CPF Incorreto!");
            return;
        }

        System.out.println("Insira a Senha do Admin:");
        String senha = SC.next().trim();
        if (!senha.equals(SENHAADMIN)){
            System.out.println("Senha Incorreta!");
            return;
        }

        menuAdmin();
    }

    public static void menuAdmin(){
        while (true){
            System.out.println("Menu Admin\n1 - Listar todas as Contas\n2 - Somar Todos os Saldos\n3 - Sair da Conta\nEscolha:");
            int opcao = SC.nextInt();
            switch (opcao){
                case 1 -> listarTodos();
                case 2 -> somarTodos();
                case 3 -> {
                    return;
                }
                default -> System.out.println("Entrada Inválida!");
            }
        }
    }

    public static void listarTodos(){
        if (saldos.isEmpty()){
            System.out.println("Nenhum Cliente Cadastrado!");
            return;
        }

        for (var cpf : saldos.keySet()){
            System.out.printf("CPF: %s | Saldo: R$%.2f%n", cpf, saldos.get(cpf));
        }
    }

    public static void somarTodos(){
        if (saldos.isEmpty()){
            System.out.println("Nenhum Cliente Cadastrado!");
            return;
        }

        double saldoTotal = saldos.values().stream().mapToDouble(Double::doubleValue).sum();
        System.out.printf("Soma Total dos Saldos: R$%.2f%n", saldoTotal);
    }

    public static double lerNumeroDouble(){
        while (true){
            try {
                double valor = Double.parseDouble(SC.next().trim().replace(",", "."));
                if (valor > 0) return valor;
                System.out.println("Insira um valor Positivo:");
            } catch (NumberFormatException e){
                System.out.println("Insira um Número Válido:");
            }
        }
    }
}