package com.pluginforge.meuprimeiroplugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * MeuPrimeiroPlugin
 * 
 * Plugin básico de boas-vindas para Minecraft Spigot 1.20.1.
 * Autor: TestUser
 * Versão: 1.0.0
 *
 * Funcionalidade principal:
 * - Comando /ola que envia uma mensagem de boas-vindas ao jogador ou ao console.
 */
public class MeuPrimeiroPlugin extends JavaPlugin {

    /**
     * Método chamado quando o plugin é habilitado pelo servidor.
     * Aqui inicializamos recursos, registramos comandos, listeners, etc.
     */
    @Override
    public void onEnable() {
        // Mensagem no console indicando que o plugin foi carregado com sucesso
        getLogger().info("MeuPrimeiroPlugin v1.0.0 habilitado com sucesso!");
    }

    /**
     * Método chamado quando o plugin é desabilitado pelo servidor.
     * Use este método para liberar recursos, salvar dados, etc.
     */
    @Override
    public void onDisable() {
        // Mensagem no console indicando que o plugin foi desabilitado
        getLogger().info("MeuPrimeiroPlugin foi desabilitado.");
    }

    /**
     * Tratamento do comando /ola.
     * 
     * Este método é chamado sempre que um comando registrado no plugin.yml
     * é executado. Aqui verificamos se o comando é o "ola" e respondemos.
     *
     * @param sender  Quem executou o comando (jogador, console, bloco de comando, etc.)
     * @param command Objeto que representa o comando executado
     * @param label   Alias do comando utilizado
     * @param args    Argumentos passados após o comando
     * @return true se o comando foi tratado com sucesso; false para exibir o uso padrão
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Verifica se o comando executado é o /ola (caso-insensível)
        if (command.getName().equalsIgnoreCase("ola")) {
            // Mensagem de boas-vindas padrão
            String mensagem = ChatColor.GREEN + "Olá! " + ChatColor.YELLOW + "Bem-vindo ao servidor!";

            // Se o remetente for um jogador, podemos personalizar a mensagem com o nome dele
            if (sender instanceof Player) {
                Player player = (Player) sender;
                String personalizada = ChatColor.GREEN + "Olá, " + ChatColor.AQUA + player.getName()
                        + ChatColor.GREEN + "! " + ChatColor.YELLOW + "Bem-vindo ao servidor!";
                player.sendMessage(personalizada);
            } else {
                // Caso seja o console ou outro tipo de remetente, envia a mensagem padrão
                sender.sendMessage(mensagem);
            }
            return true;
        }

        // Caso o comando não seja reconhecido por este plugin, retornamos false
        // para que o Spigot mostre a mensagem de uso padrão (se configurada).
        return false;
    }
}
