package org.example.mapper;

import org.example.DTOs.EstadisticasJugadoresResponse;
import org.example.DTOs.EstadisticasParejasResponse;
import org.example.DTOs.ParejaDTO;
import org.example.Model.Pareja;
import org.example.Model.User;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Clase que se encarga de mapear (convertir) un objeto de tipo Pareja a un objeto de tipo ParejaDTO.
 */
public class ParejaMapper {
    /**
     * Convierte un objeto Pareja en un objeto ParejaDTO.
     *
     * @param pareja El objeto Pareja a ser mapeado.
     * @return El objeto ParejaDTO mapeado.
     */
    public ParejaDTO mapParejaToParejaDto(Pareja pareja){
        ParejaDTO parejaDto=new ParejaDTO();
        parejaDto.setId(Integer.parseInt(pareja.getId().toString()));
        parejaDto.setNombrePareja(pareja.getJugador1().getName() + " - " +pareja.getJugador2().getName());
        parejaDto.setJugador1(Integer.parseInt(pareja.getJugador1().getId().toString()));
        parejaDto.setJugador2(Integer.parseInt(pareja.getJugador2().getId().toString()));
        parejaDto.setP_ganadas(pareja.getPartidasGanadas().size());
        parejaDto.setP_jugadas(pareja.getPartidasPerdidas().size() + pareja.getPartidasGanadas().size());
        parejaDto.setP_perdidas(pareja.getPartidasPerdidas().size());
        parejaDto.setNombre_jugador1(pareja.getJugador1().getName());
        parejaDto.setNombre_jugador2(pareja.getJugador2().getName());

        return parejaDto;

    }

    public EstadisticasParejasResponse getEstadisticasParejasReponse(List<Pareja> all) {
        int size = all.size();
        String[] names = new String[size];
        String[] namesPorcentaje = new String[size];
        int[] partidasGanadas = new int[size];
        int[] partidasPerdidas = new int[size];
        double[] porcentajeVictorias = new double[size];

        for (int i = 0; i < size; i++) {
            Pareja pareja = all.get(i);
            names[i] = pareja.getJugador1().getName() + " - " + pareja.getJugador2().getName();
            partidasGanadas[i] = OptionalInt.of(pareja.getPartidasGanadas().size()).orElse(0);
            partidasPerdidas[i] = OptionalInt.of(pareja.getPartidasPerdidas().size()).orElse(0);
            namesPorcentaje[i] = pareja.getJugador1().getName() + " - " + pareja.getJugador2().getName();
            porcentajeVictorias[i]=calcularPorcentajeVictorias(pareja);
        }

        // Sort arrays
        sortArrays(names, partidasGanadas, partidasPerdidas);
        sortArraysPorcentaje(namesPorcentaje,porcentajeVictorias);

        // Set values in EstadisticasJugadoresResponse
        EstadisticasParejasResponse estadisticasParejasResponse = new EstadisticasParejasResponse();
        estadisticasParejasResponse.setNames(names);
        estadisticasParejasResponse.setPartidasGanadas(partidasGanadas);
        estadisticasParejasResponse.setPartidasPerdidas(partidasPerdidas);
        estadisticasParejasResponse.setNamesPorcentaje(namesPorcentaje);
        estadisticasParejasResponse.setPorcentajeVictorias(porcentajeVictorias);

        return estadisticasParejasResponse;
    }

    public EstadisticasParejasResponse getEstadisticasParejasReponsePorJugador(List<Pareja> all,String name) {
        int size = all.size();
        String[] names = new String[size];
        String[] namesPorcentaje = new String[size];
        int[] partidasGanadas = new int[size];
        int[] partidasPerdidas = new int[size];
        double[] porcentajeVictorias = new double[size];

        for (int i = 0; i < size; i++) {
            Pareja pareja = all.get(i);
            names[i] = pareja.getJugador1().getName().equals(name)?pareja.getJugador2().getName():pareja.getJugador1().getName();
            partidasGanadas[i] = OptionalInt.of(pareja.getPartidasGanadas().size()).orElse(0);
            partidasPerdidas[i] = OptionalInt.of(pareja.getPartidasPerdidas().size()).orElse(0);
            namesPorcentaje[i] = pareja.getJugador1().getName().equals(name)?pareja.getJugador2().getName():pareja.getJugador1().getName();
            porcentajeVictorias[i]=calcularPorcentajeVictorias(pareja);
        }

        // Sort arrays
        sortArrays(names, partidasGanadas, partidasPerdidas);
        sortArraysPorcentaje(namesPorcentaje,porcentajeVictorias);

        EstadisticasParejasResponse estadisticasParejasResponse = new EstadisticasParejasResponse();
        estadisticasParejasResponse.setNames(names);
        estadisticasParejasResponse.setPartidasGanadas(partidasGanadas);
        estadisticasParejasResponse.setPartidasPerdidas(partidasPerdidas);
        estadisticasParejasResponse.setNamesPorcentaje(namesPorcentaje);
        estadisticasParejasResponse.setPorcentajeVictorias(porcentajeVictorias);

        return estadisticasParejasResponse;
    }

    private void sortArrays(String[] names, int[] partidasGanadas, int[] partidasPerdidas) {
        // Sort arrays based on partidasGanadas
        for (int i = 0; i < partidasGanadas.length - 1; i++) {
            for (int j = 0; j < partidasGanadas.length - i - 1; j++) {
                if (partidasGanadas[j] < partidasGanadas[j + 1]) {
                    // Swap names
                    String tempUsername = names[j];
                    names[j] = names[j + 1];
                    names[j + 1] = tempUsername;
                    // Swap partidasGanadas
                    int tempGanadas = partidasGanadas[j];
                    partidasGanadas[j] = partidasGanadas[j + 1];
                    partidasGanadas[j + 1] = tempGanadas;
                    // Swap partidasPerdidas
                    int tempPerdidas = partidasPerdidas[j];
                    partidasPerdidas[j] = partidasPerdidas[j + 1];
                    partidasPerdidas[j + 1] = tempPerdidas;
                }
            }
        }
    }
    private void sortArraysPorcentaje(String[] namesPorcentaje, double[] porcentajeVictorias) {
        // Sort arrays based on porcentajeVictorias
        for (int i = 0; i < porcentajeVictorias.length - 1; i++) {
            for (int j = 0; j < porcentajeVictorias.length - i - 1; j++) {
                if (porcentajeVictorias[j] < porcentajeVictorias[j + 1]) {
                    // Swap names
                    String tempUsername = namesPorcentaje[j];
                    namesPorcentaje[j] = namesPorcentaje[j + 1];
                    namesPorcentaje[j + 1] = tempUsername;
                    // Swap porcentajeVictorias
                    double tempGanadas = porcentajeVictorias[j];
                    porcentajeVictorias[j] = porcentajeVictorias[j + 1];
                    porcentajeVictorias[j + 1] = tempGanadas;
                }
            }
        }
    }

    public double calcularPorcentajeVictorias(Pareja pareja) {
        int partidasGanadas = OptionalInt.of(pareja.getPartidasGanadas().size()).orElse(0);
        int partidasPerdidas = OptionalInt.of(pareja.getPartidasPerdidas().size()).orElse(0);

        if (partidasGanadas + partidasPerdidas == 0) {
            return 0.0;
        }

        return (double) partidasGanadas / (partidasGanadas + partidasPerdidas) * 100.0;
    }
}
