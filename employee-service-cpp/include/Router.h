#pragma once

#include <crow.h>
#include "EmployeeController.h"

/**
 * Router class for configuring API routes
 */
class Router {
public:
    // Configure all routes for the application
    template<typename... Middlewares>
    static void configureRoutes(crow::Crow<Middlewares...>& app);

private:
    // Configure employee routes
    template<typename... Middlewares>
    static void configureEmployeeRoutes(crow::Crow<Middlewares...>& app);
    
    // Configure health routes
    template<typename... Middlewares>
    static void configureHealthRoutes(crow::Crow<Middlewares...>& app);
};
