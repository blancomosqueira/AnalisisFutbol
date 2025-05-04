from django.shortcuts import render
from rest_framework import viewsets
from .models import Jugador, Evaluacion
from .serializers import JugadorSerializer, EvaluacionSerializer

class JugadorViewSet(viewsets.ModelViewSet):
    queryset = Jugador.objects.all()
    serializer_class = JugadorSerializer

class EvaluacionViewSet(viewsets.ModelViewSet):
    queryset = Evaluacion.objects.all()
    serializer_class = EvaluacionSerializer

