from rest_framework import viewsets
from .models import Jugador, Equipo
from .serializers import JugadorSerializer, EquipoSerializer

# Vista para jugadores
class JugadorViewSet(viewsets.ModelViewSet):
    queryset = Jugador.objects.all()
    serializer_class = JugadorSerializer

# Vista para equipos
class EquipoViewSet(viewsets.ModelViewSet):
    queryset = Equipo.objects.all()
    serializer_class = EquipoSerializer
