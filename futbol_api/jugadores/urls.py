from django.urls import path, include
from rest_framework.routers import DefaultRouter
from .views import JugadorViewSet, EquipoViewSet, ActualizarValoracionView, EvaluacionesPorJugador,GuardarEvaluacionesAPIView # Importa la vista personalizada

router = DefaultRouter()
router.register(r'jugadores', JugadorViewSet)
router.register(r'equipos', EquipoViewSet)

urlpatterns = [
    path('', include(router.urls)),  # Incluye las rutas del router
    # Ruta personalizada para actualizar valoraciones
    path('jugadores/<int:jugador_id>/valoracion/', ActualizarValoracionView.as_view(), name='actualizar_valoracion'),
    path('jugadores/<int:jugador_id>/evaluaciones/', EvaluacionesPorJugador.as_view(), name='evaluaciones-por-jugador'),
    path('guardar_evaluaciones/', GuardarEvaluacionesAPIView.as_view(), name='guardar-evaluaciones'),
]
