#pragma once

#include <crow.h>
#include "EmployeeController.h"

/**
 * Router class for configuring API routes
 */
class Router {
public:
    // Configure all routes for the application
    static void configureRoutes(crow::App<crow::CORSHandler>& app);

private:
    // Configure employee routes
    static void configureEmployeeRoutes(crow::App<crow::CORSHandler>& app);
};
