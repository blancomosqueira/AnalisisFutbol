from rest_framework import viewsets
from .models import Jugador, Equipo
from .serializers import JugadorSerializer, EquipoSerializer
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .models import Jugador, Evaluacion

class JugadorViewSet(viewsets.ModelViewSet):
    queryset = Jugador.objects.all()
    serializer_class = JugadorSerializer

    def get_queryset(self):
        equipo_id = self.request.query_params.get('equipo')
        if equipo_id:
            return Jugador.objects.filter(equipo_id=equipo_id)
        return super().get_queryset()

class EquipoViewSet(viewsets.ModelViewSet):
    queryset = Equipo.objects.all()
    serializer_class = EquipoSerializer



class ActualizarValoracionView(APIView):
    def post(self, request, jugador_id):
        try:
            jugador = Jugador.objects.get(id=jugador_id)
            data = request.data

            # Borrar valoraciones antiguas
            Evaluacion.objects.filter(jugador=jugador).delete()

            for accion, valor in data.items():
                Evaluacion.objects.create(
                    jugador=jugador,
                    accion=accion,
                    tipo='ofensiva',  # puedes extender esto si tienes defensiva
                    valoracion=int(valor)
                )

            # Actualizar valoración media
            valores = list(map(int, data.values()))
            jugador.valoracion_media = sum(valores) / len(valores)
            jugador.save()

            return Response({'message': 'Valoraciones guardadas'}, status=status.HTTP_200_OK)
        except Jugador.DoesNotExist:
            return Response({'error': 'Jugador no encontrado'}, status=status.HTTP_404_NOT_FOUND)
        except Exception as e:
            return Response({'error': str(e)}, status=status.HTTP_400_BAD_REQUEST)


class EvaluacionesPorJugador(APIView):
    def get(self, request, jugador_id):
        evaluaciones = Evaluacion.objects.filter(jugador__id=jugador_id)
        data = {e.accion: e.valoracion for e in evaluaciones}
        return Response(data)


class GuardarEvaluacionesAPIView(APIView):
    def post(self, request):
        jugador_id = request.data.get('jugador')
        evaluaciones = request.data.get('evaluaciones')

        if not jugador_id or not evaluaciones:
            return Response({'error': 'Datos incompletos'}, status=status.HTTP_400_BAD_REQUEST)

        try:
            jugador = Jugador.objects.get(id=jugador_id)
        except Jugador.DoesNotExist:
            return Response({'error': 'Jugador no encontrado'}, status=status.HTTP_404_NOT_FOUND)

        for accion, valor in evaluaciones.items():
            Evaluacion.objects.create(
                jugador=jugador,
                accion=accion,
                tipo='ofensiva',  # Puedes hacer esto dinámico si lo necesitas
                valoracion=valor
            )

        return Response({'success': True}, status=status.HTTP_201_CREATED)