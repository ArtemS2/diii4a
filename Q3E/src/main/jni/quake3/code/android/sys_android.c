#include "q3e/q3e_android.h"

#define Q3E_GAME_NAME "Quake3"
#define Q3E_IS_INITIALIZED (com_fullyInitialized)
#define Q3E_PRINTF Com_Printf
#define Q3E_WID_RESTART CL_Vid_Restart_f()
#define Q3E_DRAW_FRAME { \
            IN_Frame( ); \
            Com_Frame( ); \
        }
#define Q3E_SHUTDOWN_GAME ShutdownGame()
#define Q3Ebool qboolean
#define Q3E_TRUE true
#define Q3E_FALSE false
#define Q3E_REQUIRE_THREAD
#define Q3E_THREAD_MAIN game_main
#define Q3E_INIT_WINDOW GLimp_AndroidInit
#define Q3E_QUIT_WINDOW GLimp_AndroidQuit
#define Q3E_CHANGE_WINDOW GLimp_AndroidInit

extern void GLimp_AndroidInit(volatile ANativeWindow *win);
extern void GLimp_AndroidQuit(void);
extern void ShutdownGame(void);

extern void CL_Vid_Restart_f(void);

#include "q3e/q3e_android.inc"

void Sys_SyncState(void)
{
	//if (setState)
	{
		static int prev_state = -1;
		/* We are in game and neither console/ui is active */
		//if (cls.state == CA_ACTIVE && Key_GetCatcher() == 0)

		int state = ((clc.state == CA_ACTIVE) && (Key_GetCatcher() == 0)) << 1;

		if (state != prev_state)
		{
			setState(state);
			prev_state = state;
		}
	}
}
