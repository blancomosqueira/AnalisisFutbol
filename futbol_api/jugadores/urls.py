from django.urls import path, include
from rest_framework.routers import DefaultRouter
from .views import JugadorViewSet, EvaluacionViewSet

router = DefaultRouter()
router.register(r'jugadores', JugadorViewSet)
router.register(r'evaluaciones', EvaluacionViewSet)

urlpatterns = [
    path('', include(router.urls)),
]
