from rest_framework import viewsets
from .models import Jugador, Equipo
from .serializers import JugadorSerializer, EquipoSerializer

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
